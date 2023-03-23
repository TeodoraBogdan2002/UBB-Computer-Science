
#pragma once
#include "repo.h"
#include "domain.h"
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "DynamicArray.h"

Repository* createRepo(DynamicArray dynamicArray)
{
    Repository* Repo = (Repository*)malloc(sizeof(Repository));
    //make sure that the space was allocated
    if (Repo == NULL)
        return NULL;

    Repo->array = dynamicArray;

    return Repo;
}

void copy_repo(Repository* repo, Repository new_repo)
{
    for (int i = 0; i < repo->array.count; i++)
        assign_values(&new_repo.array.data[i], repo->array.data[i]);
}

Repository duplicate_repository(Repository* repo)
{
    Repository new_repo;
    new_repo.array.max_length = repo->array.max_length;
    new_repo.array.count = repo->array.count;

    new_repo.array.data = (Product*)malloc(new_repo.array.max_length * sizeof(Product));

    copy_repo(repo, new_repo);
    return new_repo;

}

void init_repo(Repository* Repository)
{
    add_product_repository(Repository, "Banana", "fruit", 100, "01/04/2023");
    add_product_repository(Repository, "Apple", "fruit", 50, "01/04/2023");
    add_product_repository(Repository, "Chicken", "meat", 10, "30/03/2023");
    add_product_repository(Repository, "Pork", "meat", 40, "26/03/2023");
    add_product_repository(Repository, "Chocolate", "sweets", 70, "06/09/2023");
    add_product_repository(Repository, "Cake", "sweets", 124, "25/03/2023");
    add_product_repository(Repository, "Milk", "dairy", 3, "16/06/2023");
    add_product_repository(Repository, "Yogurt", "dairy", 5, "16/06/2023");
    add_product_repository(Repository, "Cherry", "fruit", 150, "01/04/2023");
    add_product_repository(Repository, "Cupcake", "sweets", 70, "18/03/2023");
}

void destroy_r(Repository* Repository)
{
    if (Repository == NULL)
        return;

    free(Repository);

}

void search_elements_by_string_repository(Repository* repo, char string[], Repository* repository)
{
    string[strlen(string) - 1] = '\0';
    if (strcmp(string, "") == 0)
    {
        for (int i = 0; i < repo->array.count; i++)
            add_product_repository(repository, repo->array.data[i].name, repo->array.data[i].type, repo->array.data[i].quantity,
                repo->array.data[i].date);
    }
    else
    {
        for (int i = 0; i < repo->array.count; i++)
            if (strstr(repo->array.data[i].name, string) != NULL)
                add_product_repository(repository, repo->array.data[i].name, repo->array.data[i].type, repo->array.data[i].quantity,
                    repo->array.data[i].date);
    }

}

int search_elements_by_category_repository(Repository* repo, char string[], Repository* repository)
{
    int count = 0;
    for (int i = 0; i < repo->array.count; i++)
        if (strstr(repo->array.data[i].type, string) != NULL)
        {
            count++;
            add_product_repository(repository, repo->array.data[i].name, repo->array.data[i].type, repo->array.data[i].quantity,
                repo->array.data[i].date);

        }

    if (count == 0)
    {
        for (int i = 0; i < repo->array.count; i++)
            add_product_repository(repository, repo->array.data[i].name, repo->array.data[i].type, repo->array.data[i].quantity,
                repo->array.data[i].date);
    }

    if (count != 0)
        return 1;
    else
        return 0;
}

int check_existence_product(Repository* repo, char name[], char type[])
{
    for (int i = 0; i < repo->array.count; i++)
        if ((strcmp(repo->array.data[i].name, name) == 0) && (strcmp(repo->array.data[i].type, type) == 0))
            return i;
    return -1;
}

int delete_product_repository(Repository* repo, char name[], char type[])
{
    int position;
    position = check_existence_product(repo, name, type);
    if (position == -1)
        return 0;
    else
    {
        delete_TElem(&repo->array, position);
        return 1;
    }
}

int update_products_quantity_repository(Repository* repo, char name[], char type[], int quantity)
{
    int position;
    position = check_existence_product(repo, name, type);
    if (position == -1)
        return 0;
    else
    {
        Product p = createProduct(name, type, quantity, getDate(&repo->array.data[position]));
        //repo->array.data[position].quantity = quantity;
        update_TElem(&repo->array, p, position);
        return 1;
    }
}


void add_product_repository(Repository* repo, char name[], char type[], int quantity, char date[])
{
    int position;
    position = check_existence_product(repo, name, type);
    if (position == -1)
    {
        Product product;
        product = createProduct(name, type, quantity, date);
        add_TElem(&repo->array, product);
    }
    else
    {
        repo->array.data[position].quantity = repo->array.data[position].quantity + quantity;
    }

}

int update_products_expiration_date_repository(Repository* repo, char name[], char type[], char date[])
{
    int position;
    position = check_existence_product(repo, name, type);
    if (position == -1)
        return 0;
    else
    {
        Product product = createProduct(name, type, getQuantity(&repo->array.data[position]), date);
        //strcpy(repo->array.data[position].date, date);
        update_TElem(&repo->array, product, position);
        return 1;
    }
}

int getCount(Repository* Repository)
{
    return Repository->array.count;
}


