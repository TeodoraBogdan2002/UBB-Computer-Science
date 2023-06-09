#pragma once


typedef struct
{
    char name[50];
    char type[50];
    int quantity;
    char date[10];

} Product;


/// With this function we create a product
/// \param name represents the name of the product
/// \param type represents the type of the product
/// \param quantity represents the quantity of the product
/// \param date represents the expiration date of the product
/// \return it returns the created product
Product createProduct(char name[], char type[], int quantity, char date[]);

/// With this function we get the name of the product
/// \param p represents the product
/// \return returns the name
char* getName(Product* product);

/// With this function we get the type of the product
/// \param p represents the product
/// \return returns the type
char* getType(Product* product);

/// With this function we get the quantity of the product
/// \param p represents the product
/// \return returns the quantity
int getQuantity(Product* product);

/// With this function we get the expiration date of the product
/// \param p represents the product
/// \return returns the expiration date of the product
char* getDate(Product* product);

/// With this function we assign the attributes of a specific product to another product
/// \param new_product represents the product which will get the attributes
/// \param product represents the product from which we take the attributes
void assign_values(Product* new_product, Product product);
