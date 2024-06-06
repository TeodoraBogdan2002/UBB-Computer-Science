from flask_sqlalchemy import SQLAlchemy
from uuid import uuid4
from sqlalchemy import Column, Integer, String, Text, Float, DateTime, ForeignKey
from sqlalchemy.orm import relationship
from sqlalchemy.ext.declarative import declarative_base
from datetime import datetime

db = SQLAlchemy()

def getDefaultID():
    return uuid4().hex

class User(db.Model):
    __tablename__ = "users"
    id = db.Column(db.String(32), primary_key=True, unique=True, default=getDefaultID)
    username = db.Column(db.Text, nullable=False, unique=True)
    email = db.Column(db.Text, nullable=False, unique=True)
    password = db.Column(db.Text, nullable=False)

class Meal(db.Model):
    __tablename__ = "meals"
    MealID = Column(Integer, primary_key=True)
    UserID = Column(String(32), ForeignKey('users.id'), nullable=False)
    ImagePath = Column(String(255), nullable=False)
    Timestamp = Column(DateTime, nullable=False, default=datetime.utcnow)
    user = relationship("User", backref="meals")

class FoodItem(db.Model):
    __tablename__ = "food_items"
    FoodItemID = Column(Integer, primary_key=True)
    MealID = Column(Integer, ForeignKey('meals.MealID'), nullable=False)
    Name = Column(String(100), nullable=False)
    Calories = Column(Float)
    Proteins = Column(Float)
    Fats = Column(Float)
    Carbohydrates = Column(Float)
    meal = relationship("Meal", backref="food_items")