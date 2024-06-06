
# import tensorflow
from flask import Flask, request, jsonify, session
from flask_cors import CORS
from flask_bcrypt import Bcrypt
from keras.src.saving.saving_lib import load_model
from keras_preprocessing import image
from sqlalchemy import func
from sqlalchemy import and_
# from tensorflow.keras.preprocessing import image
# from tensorflow.python.keras.models import load_model

from flask_session import Session
from models import db, User, Meal, FoodItem
import secrets
# from flask import render_template
import csv
import math
import os
import numpy as np
from datetime import datetime

from werkzeug.utils import secure_filename

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = r'sqlite:///./db.sqlite3'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
app.config['SQLALCHEMY_ECHO'] = True
app.config['SECRET_KEY'] = secrets.token_hex(16)
app.config["SESSION_PERMANENT"] = False
app.config["SESSION_TYPE"] = "filesystem"

Session(app)
CORS(app, supports_credentials=True)
bcrypt = Bcrypt(app)

db.init_app(app)
with app.app_context():
    db.create_all()


UPLOAD_FOLDER = 'uploads'
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER

# define label meaning
label = ['apple pie',
         'baby back ribs',
         'baklava',
         'beef carpaccio',
         'beef tartare',
         'beet salad',
         'beignets',
         'bibimbap',
         'bread pudding',
         'breakfast burrito',
         'bruschetta',
         'caesar salad',
         'cannoli',
         'caprese salad',
         'carrot cake',
         'ceviche',
         'cheese plate',
         'cheesecake',
         'chicken curry',
         'chicken quesadilla',
         'chicken wings',
         'chocolate cake',
         'chocolate mousse',
         'churros',
         'clam chowder',
         'club sandwich',
         'crab cakes',
         'creme brulee',
         'croque madame',
         'cup cakes',
         'deviled eggs',
         'donuts',
         'dumplings',
         'edamame',
         'eggs benedict',
         'escargots',
         'falafel',
         'filet mignon',
         'fish and_chips',
         'foie gras',
         'french fries',
         'french onion soup',
         'french toast',
         'fried calamari',
         'fried rice',
         'frozen yogurt',
         'garlic bread',
         'gnocchi',
         'greek salad',
         'grilled cheese sandwich',
         'grilled salmon',
         'guacamole',
         'gyoza',
         'hamburger',
         'hot and sour soup',
         'hot dog',
         'huevos rancheros',
         'hummus',
         'ice cream',
         'lasagna',
         'lobster bisque',
         'lobster roll sandwich',
         'macaroni and cheese',
         'macarons',
         'miso soup',
         'mussels',
         'nachos',
         'omelette',
         'onion rings',
         'oysters',
         'pad thai',
         'paella',
         'pancakes',
         'panna cotta',
         'peking duck',
         'pho',
         'pizza',
         'pork chop',
         'poutine',
         'prime rib',
         'pulled pork sandwich',
         'ramen',
         'ravioli',
         'red velvet cake',
         'risotto',
         'samosa',
         'sashimi',
         'scallops',
         'seaweed salad',
         'shrimp and grits',
         'spaghetti bolognese',
         'spaghetti carbonara',
         'spring rolls',
         'steak',
         'strawberry shortcake',
         'sushi',
         'tacos',
         'octopus balls',
         'tiramisu',
         'tuna tartare',
         'waffles']

nu_link = 'https://www.nutritionix.com/food/'

# Loading the best saved model to make predictions.
try:
    model_best = load_model('C:\\Users\\teove\\Documents\\Faculty\\Licenta\\AI\\content\\best_model_101classvardoi.keras', compile=False)
    print('Model successfully loaded!')
except Exception as e:
    print('Error loading the model:', e)
# print(model_best.summary())
# print('Input shape:', model_best.input_shape)
# print('Output shape:', model_best.output_shape)
# print('Model layers:')
# for layer in model_best.layers:
#     print(layer.name)

nutrients = [
    {'name': 'protein', 'value': 0.0},
    {'name': 'fat', 'value': 0.0},
    {'name': 'carbohydrates', 'value': 0.0},
    {'name': 'kilocalories', 'value': 0.0},
    {'name': 'calcium', 'value': 0.0},
    {'name': 'vitamins', 'value': 0.0}
]

with open('nutrition101.csv', 'r') as file:
    reader = csv.reader(file)
    nutrition_table = dict()
    for i, row in enumerate(reader):
        if i == 0:
            name = ''
            continue
        else:
            name = row[0].strip()
        nutrition_table[name] = [
            {'name': 'name', 'value': row[0]},
            {'name': 'protein', 'value': float(row[1])},
            {'name': 'fat', 'value': float(row[2])},
            {'name': 'carbohydrates', 'value': float(row[3])},
            {'name': 'kilocalories', 'value': float(row[4])},
            {'name': 'calcium', 'value': float(row[5])},
            {'name': 'vitamins', 'value': float(row[6])}
        ]

start = [0]
passed = [0]
pack = [[]]
num = [0]

@app.route("/@me", methods=['GET'])
def getCurrentUser():
    userId = session.get("user")

    if not userId:
        return jsonify({'msg': 'unauthorized'}), 401

    user = User.query.filter_by(id=userId).first()
    return jsonify({'id': user.id, 'username': user.username})


@app.route('/signup', methods=['POST'])
def signUp():
    username = request.json['username']


    email = request.json['email']

    userExists = User.query.filter_by(username=username).first() is not None
    emailExists = User.query.filter_by(email=email).first() is not None

    if userExists or emailExists:
        return jsonify({'msg': 'user or email exists'}), 409
    
    password = bcrypt.generate_password_hash(request.json['password'])
    user = User(username=username, email=email, password=password)
    db.session.add(user)
    db.session.commit()

    return jsonify(user.id)

@app.route('/login', methods=['POST'])
def login():
    username = request.json['username']
    password = request.json['password']

    user = User.query.filter_by(username=username).first()

    if user is None:
        return jsonify({'msg': 'incorrect user or password'}), 401
    
    if not bcrypt.check_password_hash(user.password, password):
        return jsonify({'msg': 'incorrect user or password'}), 401

    session['user'] = user.id
    return jsonify(
        {
        'user': user.username
        }
    )

@app.route('/logout', methods=['GET'])
def logout():
    session.pop('user')
    return "200"

@app.route('/upload', methods=['POST'])
def upload():
    file = request.files.getlist("img")

    # # Get current user's ID using getCurrentUser function
    user_response = getCurrentUser()
    if user_response.status_code != 200:
        return jsonify({'error': 'User not authorized'}), 401  # Return error response if user is not authorized

    user_data = user_response.json
    user_id = user_data['id']

    # Get the maximum MealID from the database
    max_meal_id = db.session.query(func.max(Meal.MealID)).scalar() or 0

    for f in file:
        # Generate a unique MealID
        meal_id = max_meal_id + 1

        # Increment the max_meal_id for the next iteration
        max_meal_id += 1

        # Generate filename and save the file
        filename = secure_filename(str(meal_id + 500) + '.jpg')
        num[0] += 1
        name = os.path.join(app.config['UPLOAD_FOLDER'], filename)
        print('save name', name)
        f.save(name)


        # Create a new Meal object
        new_meal = Meal(
            MealID=meal_id,
            UserID=user_id,
            ImagePath=name,
            Timestamp=datetime.utcnow()
        )

        # Add the new meal to the database session
        db.session.add(new_meal)

        # Append the meal information to pack
        # pack[0].append({
        #     'MealID': meal_id,
        #     'UserID': user_id,
        #     'ImagePath': name,
        #     'Timestamp': datetime.utcnow()
        # })
        # meal_ids.append(meal_id)

    # Commit changes to the database
    db.session.commit()

    return jsonify({'message': 'Upload successful', 'meal_id': meal_id})
    # file = request.files.getlist("img")
    # for f in file:
    #     filename = secure_filename(str(num[0] + 500) + '.jpg')
    #     num[0] += 1
    #     name = os.path.join(app.config['UPLOAD_FOLDER'], filename)
    #     print('save name', name)
    #     f.save(name)
    #
    # pack[0] = []
    # return render_template('recognize.html', img=file)


@app.route('/meals/<userid>/<date>', methods=['GET'])
def get_meals_by_user_and_date(userid, date):
    try:
        # Convert the date string to a datetime object
        query_date = datetime.strptime(date, '%Y-%m-%d')

        # Define the start and end of the day
        start_of_day = datetime(query_date.year, query_date.month, query_date.day, 0, 0, 0)
        end_of_day = datetime(query_date.year, query_date.month, query_date.day, 23, 59, 59)

        # Query the database for meals of the user on the specified date
        meals = Meal.query.filter(
            and_(
                Meal.UserID == userid,
                Meal.Timestamp >= start_of_day,
                Meal.Timestamp <= end_of_day
            )
        ).all()

        # Serialize the results
        meal_list = []
        for meal in meals:
            food_item = FoodItem.query.filter_by(MealID=meal.MealID).first()
            if food_item:
                meal_data = {
                    'MealID': meal.MealID,
                    'UserID': meal.UserID,
                    'ImagePath': meal.ImagePath,
                    'Timestamp': meal.Timestamp.strftime('%Y-%m-%d %H:%M:%S'),
                    'FoodName': food_item.Name,
                    'Calories': food_item.Calories,
                    'Proteins': food_item.Proteins,
                    'Fats': food_item.Fats,
                    'Carbohydrates': food_item.Carbohydrates
                }
                meal_list.append(meal_data)
            else:
                meal_data = {
                    'MealID': meal.MealID,
                    'UserID': meal.UserID,
                    'ImagePath': meal.ImagePath,
                    'Timestamp': meal.Timestamp.strftime('%Y-%m-%d %H:%M:%S'),
                    'FoodName': None,
                    'Calories': None,
                    'Proteins': None,
                    'Fats': None,
                    'Carbohydrates': None
                }
                meal_list.append(meal_data)

        return jsonify(meal_list), 200

    except ValueError:
        return jsonify({'error': 'Invalid date format. Use YYYY-MM-DD.'}), 400

    except Exception as e:
        return jsonify({'error': str(e)}), 500

@app.route("/meals/dates", methods=['GET'])
def get_meal_dates():
    user_id = session.get("user")

    if not user_id:
        return jsonify({'msg': 'unauthorized'}), 401

    meal_dates = db.session.query(func.date(Meal.Timestamp)).filter_by(UserID=user_id).distinct().all()

    dates = [date[0] for date in meal_dates]

    return jsonify({'meal_dates': dates})

@app.route("/meals/dates/<username>", methods=['GET'])
def get_meal_dates_by_username(username):
    # Find the user by username
    user = User.query.filter_by(username=username).first()

    if not user:
        return jsonify({'msg': 'user not found'}), 404

    user_id = user.id

    # Query to get all distinct dates of meals added by the user
    meal_dates = db.session.query(func.date(Meal.Timestamp)).filter_by(UserID=user_id).distinct().all()

    # Extract dates from the query result
    dates = [date[0] for date in meal_dates]

    return jsonify({'meal_dates': dates})

@app.route('/meals', methods=['GET'])
def get_meals_by_date():
    # Get the current user
    user_response = getCurrentUser()
    if user_response.status_code != 200:
        return jsonify({'error': 'User not authorized'}), 401

    user_data = user_response.json()
    user_id = user_data['id']

    # Get the date parameter from the query string
    date_str = request.args.get('date')
    if not date_str:
        return jsonify({'error': 'Date parameter is required'}), 400

    try:
        # Parse the date string into a date object
        date = datetime.strptime(date_str, '%Y-%m-%d').date()
    except ValueError:
        return jsonify({'error': 'Invalid date format. Use YYYY-MM-DD format.'}), 400

    # Query the database for meals associated with the user on the specified date
    meals = Meal.query.filter(
        and_(
            Meal.UserID == user_id,
            db.func.date(Meal.Timestamp) == date
        )
    ).all()

    # Format the meals data
    meals_data = []
    for meal in meals:
        meal_data = {
            'MealID': meal.MealID,
            'ImagePath': meal.ImagePath,
            'Timestamp': meal.Timestamp.isoformat(),
            'food_items': []
        }
        for food_item in meal.food_items:
            meal_data['food_items'].append({
                'FoodItemID': food_item.FoodItemID,
                'Name': food_item.Name,
                'Calories': food_item.Calories,
                'Proteins': food_item.Proteins,
                'Fats': food_item.Fats,
                'Carbohydrates': food_item.Carbohydrates
            })
        meals_data.append(meal_data)

    return jsonify(meals_data)


@app.route('/predict', methods=['POST'])
def predict():
    user_response = getCurrentUser()
    if user_response.status_code != 200:
        return jsonify({'error': 'User not authorized'}), 401

    user_data = user_response.json
    user_id = user_data['id']
    # pa = dict()
    max_meal_id = db.session.query(func.max(Meal.MealID)).scalar() or 0
    meal_id = max_meal_id
    max_meal_id += 1

    filename = f'{UPLOAD_FOLDER}/{meal_id + 500}.jpg'
    # filenamee = secure_filename(str(meal_id + 500) + '.jpg')

    # name = os.path.join(app.config['UPLOAD_FOLDER'], filenamee)

    # pack[0].append({
    #     'MealID': meal_id,
    #     'UserID': user_id,
    #     'ImagePath': name,
    #     'Timestamp': datetime.utcnow()
    # })

    print('image filepath', filename)
    pred_img = filename
    pred_img = image.load_img(pred_img, target_size=(250, 250))
    pred_img = image.img_to_array(pred_img)
    pred_img = np.expand_dims(pred_img, axis=0)
    pred_img = pred_img / 255.

    pred = model_best.predict(pred_img)
    print("Pred")
    print(pred)

    if math.isnan(pred[0][0]) and math.isnan(pred[0][1]) and \
            math.isnan(pred[0][2]) and math.isnan(pred[0][3]):
        pred = np.array([0.05, 0.05, 0.05, 0.07, 0.09, 0.19, \
                         0.55, 0.0, 0.0, 0.0, 0.0])

    top = pred.argsort()[0][-3:]
    label.sort()
    _true = label[top[2]]
    # pa['image'] = f'{UPLOAD_FOLDER}/{meal_id + 500}.jpg'
    # x = dict()
    # x[_true] = float("{:.2f}".format(pred[0][top[2]] * 100))
    # x[label[top[1]]] = float("{:.2f}".format(pred[0][top[1]] * 100))
    # x[label[top[0]]] = float("{:.2f}".format(pred[0][top[0]] * 100))
    # pa['result'] = x
    # pa['nutrition'] = nutrition_table[_true]
    # pa['food'] = f'{nu_link}{_true}'
    # pa['idx'] = meal_id
    # pa['quantity'] = 100

    # pack[0].append(pa)
    # passed[0] += 1
    new_food_item = FoodItem(
        MealID=meal_id,
        Name=nutrition_table[_true][0]['value'],
        Calories=nutrition_table[_true][4]['value'],
        Proteins=nutrition_table[_true][1]['value'],
        Fats=nutrition_table[_true][2]['value'],
        Carbohydrates=nutrition_table[_true][3]['value']
    )
    db.session.add(new_food_item)
    db.session.commit()

    # start[0] = passed[0]
    print('successfully packed')

    # print("Pack:", pack[0])

    return jsonify({'food_item_name': new_food_item.Name, 'proteins': new_food_item.Proteins, 'fats': new_food_item.Fats, 'calories': new_food_item.Calories, 'calcium': nutrition_table[_true][5]['value'], 'vitamis': nutrition_table[_true][6]['value']})



if __name__ == "__main__":
    app.run(debug=True)
    # all_users = User.query.all()
    #
    # # Print all users
    # for user in all_users:
    #     print(user.id, user.username, user.email)
    # all_meals = Meal.query.all()
    #
    # # Print all users
    # for meal in all_meals:
    #     print(meal.id, meal.UserID, meal.ImagePath)

    # print("Nutrition Table:")
    # for food_item, nutrition_info in nutrition_table.items():
    #     print(f"Food Item: {food_item}")
    #     for nutrient in nutrition_info:
    #         print(f"- {nutrient['name']}: {nutrient['value']}")
    #     print()

