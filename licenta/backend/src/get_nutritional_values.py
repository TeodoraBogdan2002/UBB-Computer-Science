import requests
import pandas as pd


def get_nutrition(food_name):
    nutrition_data = []
    for name in food_name:
        url = "https://api.nal.usda.gov/fdc/v1/foods/search?api_key=d4D6dSOc81pTAOY2gsNZ0YhjkMlhStLJRoII5SJu&query=" + name
        response = requests.get(url)
        data = response.json()
        flatten_json = pd.json_normalize(data["foods"])
        first_food = flatten_json.iloc[0]
        first_food_nutrition_list = first_food.foodNutrients

        nutrition_dict = {'name': name}
        for item in first_food_nutrition_list:
            if item['nutrientNumber'] == "208":
                nutrition_dict['kilocalories'] = item['value']
            elif item['nutrientNumber'] == "203":
                nutrition_dict['protein'] = item['value']
            elif item['nutrientNumber'] == "301":
                nutrition_dict['calcium'] = item['value'] / 1000
            elif item['nutrientNumber'] == "204":
                nutrition_dict['fat'] = item['value']
            elif item['nutrientNumber'] == "205":
                nutrition_dict['carbohydrates'] = item['value']
            elif item['nutrientNumber'] == "318":
                vitamin_a = item['value']
            elif item['nutrientNumber'] == "401":
                vitamin_c = item['value']

        vitamins = (float(vitamin_a) + float(vitamin_c)) / 1000
        nutrition_dict['vitamins'] = vitamins
        nutrition_data.append(nutrition_dict)
    return nutrition_data


nutrition101 = get_nutrition(['apple pie',
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
                             )
nutrition101_df = pd.DataFrame(nutrition101)
nutrition101_df.to_csv("nutrition101.csv", index=False)
