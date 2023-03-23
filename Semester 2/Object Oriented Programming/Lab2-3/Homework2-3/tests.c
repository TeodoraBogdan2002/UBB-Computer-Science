#include <stdlib.h>
#include "tests.h"
#include "domain.h"
#include "repo.h"
#include "undo_redo.h"
#include "validation.h"
#include "controller.h"
#include <assert.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <crtdbg.h>

void test_createDynamicArray()
{
    DynamicArray* DynamicArray = createDynamicArray(100);
    assert(DynamicArray->count == 0);
    assert(DynamicArray->max_length == 100);
    destroy(DynamicArray);
}

void test_addTElem()
{
    Product product = createProduct("Banana", "fruit", 100, "11/12/2023");
    DynamicArray* array = createDynamicArray(100);
    add_TElem(array, product);
    assert(strcmp(array->data[0].name, "Banana") == 0);
    assert(strcmp(array->data[0].type, "fruit") == 0);
    assert(array->data[0].quantity == 100);
    assert(strcmp(array->data[0].date, "11/12/2023") == 0);
    destroy(array);

}

void test_delete_TELEM()
{
    Product product = createProduct("Banana", "fruit", 100, "11/12/2023");
    Product product2 = createProduct("Apple", "fruit", 100, "11/09/2023");
    DynamicArray* array = createDynamicArray(100);
    add_TElem(array, product);
    add_TElem(array, product2);
    assert(strcmp(array->data[0].name, "Banana") == 0);
    assert(strcmp(array->data[0].type, "fruit") == 0);
    assert(array->data[0].quantity == 100);
    assert(strcmp(array->data[0].date, "11/12/2023") == 0);
    delete_TElem(array, 0);
    assert(strcmp(array->data[0].name, "Apple") == 0);
    assert(strcmp(array->data[0].type, "fruit") == 0);
    assert(array->data[0].quantity == 100);
    assert(strcmp(array->data[0].date, "11/09/2023") == 0);
    destroy(array);

}

void test_update_TElem()
{
    Product product = createProduct("Banana", "fruit", 100, "11/12/2023");
    Product product2 = createProduct("Apple", "fruit", 100, "09/09/2040");
    DynamicArray* array = createDynamicArray(100);
    add_TElem(array, product);
    assert(strcmp(array->data[0].name, "Banana") == 0);
    assert(strcmp(array->data[0].type, "fruit") == 0);
    assert(array->data[0].quantity == 100);
    assert(strcmp(array->data[0].date, "11/12/2023") == 0);
    update_TElem(array, product2, 0);
    assert(strcmp(array->data[0].date, "09/09/2040") == 0);
    destroy(array);
}

void test_domain_getters()
{
    Product product = createProduct("Banana", "fruit", 100, "11/12/2023");
    assert(strcmp(getName(&product), "Banana") == 0);
    assert(strcmp(getType(&product), "fruit") == 0);
    assert(getQuantity(&product) == 100);
    assert(strcmp(getDate(&product), "11/12/2023") == 0);

}

void test_createProduct()
{
    Product product = createProduct("Banana", "fruit", 100, "11/12/2023");
    assert(strcmp(product.name, "Banana") == 0);
    assert(strcmp(product.type, "fruit") == 0);
    assert(product.quantity == 100);
    assert(strcmp(product.date, "11/12/2023") == 0);
}

void test_createRepo()
{
    DynamicArray* DynamicArray = createDynamicArray(100);
    Repository* repo = createRepo(*DynamicArray);
    init_repo(repo);
    assert(repo->array.max_length == 100);
    assert(repo->array.count == 10);
    assert(strcmp(repo->array.data[0].name, "Banana") == 0);
    assert(strcmp(repo->array.data[0].type, "fruit") == 0);
    assert(repo->array.data[0].quantity == 100);
    assert(strcmp(repo->array.data[0].date, "01/04/2022") == 0);
    destroy_r(repo);
    destroy(DynamicArray);
}

void test_assign_values()
{
    Product product = createProduct("Banana", "fruit", 100, "11/12/2023");
    Product new_product;
    assign_values(&new_product, product);
    assert(strcmp(product.name, new_product.name) == 0);
    assert(strcmp(product.type, new_product.type) == 0);
    assert(product.quantity == new_product.quantity);
    assert(strcmp(product.date, new_product.date) == 0);

}

void test_getCount()
{
    DynamicArray* DynamicArray = createDynamicArray(100);
    Repository* repo = createRepo(*DynamicArray);
    init_repo(repo);
    assert(getCount(repo) == 10);
    destroy_r(repo);
    destroy(DynamicArray);
}

void test_add_product_r()
{
    DynamicArray* DynamicArray = createDynamicArray(100);
    Repository* repo = createRepo(*DynamicArray);
    init_repo(repo);
    add_product_repository(repo, "Strawberry", "fruit", 100, "11/12/2034");
    assert(strcmp(repo->array.data[10].name, "Strawberry") == 0);
    assert(strcmp(repo->array.data[10].type, "fruit") == 0);
    assert(repo->array.data[10].quantity == 100);
    assert(strcmp(repo->array.data[10].date, "11/12/2034") == 0);
    destroy_r(repo);
    destroy(DynamicArray);
}

void test_delete_product_repository()
{
    DynamicArray* DynamicArray = createDynamicArray(100);
    Repository* repo = createRepo(*DynamicArray);
    init_repo(repo);
    assert(strcmp(repo->array.data[0].name, "Banana") == 0);
    assert(strcmp(repo->array.data[0].type, "fruit") == 0);
    assert(repo->array.data[0].quantity == 100);
    assert(strcmp(repo->array.data[0].date, "01/04/2022") == 0);
    assert(repo->array.count == 10);
    assert(delete_product_repository(repo, "Banana", "fruit") == 1);
    assert(repo->array.count == 9);
    assert(strcmp(repo->array.data[0].name, "Apple") == 0);
    assert(strcmp(repo->array.data[0].type, "fruit") == 0);
    assert(repo->array.data[0].quantity == 50);
    assert(strcmp(repo->array.data[0].date, "01/04/2021") == 0);
    assert(delete_product_repository(repo, "SoyaMilk", "dairy") == 0);
    destroy_r(repo);
    destroy(DynamicArray);

}

void test_update_products_quantity_repository()
{
    DynamicArray* DyanmicArray = createDynamicArray(100);
    Repository* repo = createRepo(*DyanmicArray);
    init_repo(repo);
    assert(strcmp(repo->array.data[0].name, "Banana") == 0);
    assert(strcmp(repo->array.data[0].type, "fruit") == 0);
    assert(repo->array.data[0].quantity == 100);
    assert(strcmp(repo->array.data[0].date, "01/04/2022") == 0);
    update_products_quantity_repository(repo, "Banana", "fruit", 999);
    assert(repo->array.data[0].quantity == 999);
    destroy_r(repo);
    destroy(DyanmicArray);

}

void test_update_expiration_date_repository()
{
    DynamicArray* DynamicArray = createDynamicArray(100);
    Repository* repo = createRepo(*DynamicArray);
    init_repo(repo);
    assert(strcmp(repo->array.data[0].name, "Banana") == 0);
    assert(strcmp(repo->array.data[0].type, "fruit") == 0);
    assert(repo->array.data[0].quantity == 100);
    assert(strcmp(repo->array.data[0].date, "01/04/2022") == 0);
    update_products_expiration_date_repository(repo, "Banana", "fruit", "09/09/9999");
    assert(strcmp(repo->array.data[0].date, "09/09/9999") == 0);
    destroy_r(repo);
    destroy(DynamicArray);
}

void test_search_elements_by_string_repository()
{
    DynamicArray* DynamicA = createDynamicArray(100);
    DynamicArray* dynamic_array = createDynamicArray(100);
    Repository* repo = createRepo(*DynamicA);
    Repository* repository = createRepo(*dynamic_array);
    init_repo(repo);
    init_repo(repository);
    delete_product_repository(repository, "Banana", "fruit");
    delete_product_repository(repository, "Apple", "fruit");
    delete_product_repository(repository, "Chicken", "meat");
    delete_product_repository(repository, "Pork", "meat");
    delete_product_repository(repository, "Chocolate", "sweets");
    delete_product_repository(repository, "Cake", "sweets");
    delete_product_repository(repository, "Milk", "dairy");
    delete_product_repository(repository, "Yogurt", "dairy");
    delete_product_repository(repository, "Cherry", "fruit");
    delete_product_repository(repository, "Cupcake", "sweets");
    search_elements_by_string_repository(repo, "\n", repository);
    assert(repository->array.count == 10);
    delete_product_repository(repository, "Banana", "fruit");
    delete_product_repository(repository, "Apple", "fruit");
    delete_product_repository(repository, "Chicken", "meat");
    delete_product_repository(repository, "Pork", "meat");
    delete_product_repository(repository, "Chocolate", "sweets");
    delete_product_repository(repository, "Cake", "sweets");
    delete_product_repository(repository, "Milk", "dairy");
    delete_product_repository(repository, "Yogurt", "dairy");
    delete_product_repository(repository, "Cherry", "fruit");
    delete_product_repository(repository, "Cupcake", "sweets");
    search_elements_by_string_repository(repo, "C\n", repository);
    assert(repository->array.count == 5);
    destroy_r(repo);
    destroy_r(repository);
    destroy(DynamicA);
    destroy(dynamic_array);

}

void test_search_elements_by_category_repository()
{
    DynamicArray* Dynamic_Array = createDynamicArray(100);
    DynamicArray* dynamic_a = createDynamicArray(100);
    Repository* repo = createRepo(*Dynamic_Array);
    Repository* repository = createRepo(*dynamic_a);
    init_repo(repo);
    init_repo(repository);
    delete_product_repository(repository, "Banana", "fruit");
    delete_product_repository(repository, "Apple", "fruit");
    delete_product_repository(repository, "Chicken", "meat");
    delete_product_repository(repository, "Pork", "meat");
    delete_product_repository(repository, "Chocolate", "sweets");
    delete_product_repository(repository, "Cake", "sweets");
    delete_product_repository(repository, "Milk", "dairy");
    delete_product_repository(repository, "Yogurt", "dairy");
    delete_product_repository(repository, "Cherry", "fruit");
    delete_product_repository(repository, "Cupcake", "sweets");
    assert(search_elements_by_category_repository(repo, "fruit", repository) == 1);
    assert(repository->array.count == 3);
    delete_product_repository(repository, "Banana", "fruit");
    delete_product_repository(repository, "Apple", "fruit");
    delete_product_repository(repository, "Cherry", "fruit");
    delete_product_repository(repo, "Banana", "fruit");
    delete_product_repository(repo, "Apple", "fruit");
    delete_product_repository(repo, "Cherry", "fruit");
    assert(search_elements_by_category_repository(repo, "fruit", repository) == 0);
    assert(repository->array.count == 7);
    destroy_r(repo);
    destroy_r(repository);
    destroy(Dynamic_Array);
    destroy(dynamic_a);

}

void test_check_existence_product()
{
    DynamicArray* DynamicArray = createDynamicArray(100);
    Repository* repo = createRepo(*DynamicArray);
    init_repo(repo);
    assert(check_existence_product(repo, "Banana", "fruit") == 0);
    assert(check_existence_product(repo, "Capsuni", "fruit") == -1);
    destroy_r(repo);
    destroy(DynamicArray);

}

void test_duplicate_repo()
{
    DynamicArray* DynamicArray = createDynamicArray(100);
    Repository* repo = createRepo(*DynamicArray);
    init_repo(repo);
    Repository new_repo = duplicate_repository(repo);
    assert(strcmp(new_repo.array.data[0].name, repo->array.data[0].name) == 0);
    assert(strcmp(new_repo.array.data[0].type, repo->array.data[0].type) == 0);
    assert(new_repo.array.data[0].quantity == repo->array.data[0].quantity);
    assert(strcmp(new_repo.array.data[0].date, repo->array.data[0].date) == 0);
    assert(new_repo.array.count == repo->array.count);
    assert(new_repo.array.max_length == repo->array.max_length);
    free(new_repo.array.data);
    destroy_r(repo);
    destroy(DynamicArray);
}

void test_copy_repo()
{
    DynamicArray* DynamicArray = createDynamicArray(100);
    Repository* repo = createRepo(*DynamicArray);
    init_repo(repo);
    Repository new_repo;
    new_repo.array.max_length = repo->array.max_length;
    new_repo.array.count = repo->array.count;
    new_repo.array.data = (Product*)malloc(new_repo.array.max_length * sizeof(Product));
    copy_repo(repo, new_repo);
    assert(strcmp(new_repo.array.data[0].name, repo->array.data[0].name) == 0);
    assert(strcmp(new_repo.array.data[0].type, repo->array.data[0].type) == 0);
    assert(new_repo.array.data[0].quantity == repo->array.data[0].quantity);
    assert(strcmp(new_repo.array.data[0].date, repo->array.data[0].date) == 0);

    assert(strcmp(new_repo.array.data[1].name, repo->array.data[1].name) == 0);
    assert(strcmp(new_repo.array.data[1].type, repo->array.data[1].type) == 0);
    assert(new_repo.array.data[1].quantity == repo->array.data[1].quantity);
    assert(strcmp(new_repo.array.data[1].date, repo->array.data[1].date) == 0);
    destroy_r(repo);
    destroy(DynamicArray);
    free(new_repo.array.data);

}

void test_add_product_service()
{
    DynamicArray* DynamicArray = createDynamicArray(100);
    Repository* repo = createRepo(*DynamicArray);
    init_repo(repo);
    UndoRepository* UndoRedo = createUndoRepo(100, repo);
    Service* service = createService(*repo, *UndoRedo);

    add_product_service(service, "Strawberry", "fruit", 100, "11/12/2034");
    assert(strcmp(service->repo.array.data[10].name, "Strawberry") == 0);
    assert(strcmp(service->repo.array.data[10].type, "fruit") == 0);
    assert(service->repo.array.data[10].quantity == 100);
    assert(strcmp(service->repo.array.data[10].date, "11/12/2034") == 0);
    destroy_service(service);
    destroy_undoRepository(UndoRedo);
    destroy_r(repo);
    destroy(DynamicArray);
}

void test_delete_product_service()
{
    DynamicArray* DynamicArray = createDynamicArray(100);
    Repository* repo = createRepo(*DynamicArray);
    init_repo(repo);
    UndoRepository* UndoRedo = createUndoRepo(100, repo);
    Service* service = createService(*repo, *UndoRedo);
    assert(strcmp(repo->array.data[0].name, "Banana") == 0);
    assert(strcmp(repo->array.data[0].type, "fruit") == 0);
    assert(repo->array.data[0].quantity == 100);
    assert(strcmp(repo->array.data[0].date, "01/04/2022") == 0);
    assert(delete_product_s(service, "Banana", "fruit") == 1);
    assert(strcmp(repo->array.data[0].name, "Apple") == 0);
    assert(strcmp(repo->array.data[0].type, "fruit") == 0);
    assert(repo->array.data[0].quantity == 50);
    assert(strcmp(repo->array.data[0].date, "01/04/2021") == 0);
    destroy_service(service);
    destroy_r(repo);
    destroy_undoRepository(UndoRedo);
    destroy(DynamicArray);

}
void test_update_products_quantity_service()
{
    DynamicArray* DynamicArray = createDynamicArray(100);
    Repository* repo = createRepo(*DynamicArray);
    init_repo(repo);
    UndoRepository* UndoRedo = createUndoRepo(100, repo);
    Service* service = createService(*repo, *UndoRedo);
    assert(strcmp(repo->array.data[0].name, "Banana") == 0);
    assert(strcmp(repo->array.data[0].type, "fruit") == 0);
    assert(repo->array.data[0].quantity == 100);
    assert(strcmp(repo->array.data[0].date, "01/04/2022") == 0);
    assert(update_products_quantity_service(service, "Banana", "fruit", 999) == 1);
    assert(repo->array.data[0].quantity == 999);
    destroy_service(service);
    destroy_r(repo);
    destroy_undoRepository(UndoRedo);
    destroy(DynamicArray);

}

void test_update_products_expiration_date_service()
{
    DynamicArray* DynamicArray = createDynamicArray(100);
    Repository* repo = createRepo(*DynamicArray);
    init_repo(repo);
    UndoRepository* UndoRedo = createUndoRepo(100, repo);
    Service* service = createService(*repo, *UndoRedo);
    assert(strcmp(repo->array.data[0].name, "Banana") == 0);
    assert(strcmp(repo->array.data[0].type, "fruit") == 0);
    assert(repo->array.data[0].quantity == 100);
    assert(strcmp(repo->array.data[0].date, "01/04/2022") == 0);
    assert(update_products_expiration_date_service(service, "Banana", "fruit", "09/09/9999") == 1);
    assert(strcmp(repo->array.data[0].date, "09/09/9999") == 0);
    destroy_service(service);
    destroy_r(repo);
    destroy_undoRepository(UndoRedo);
    destroy(DynamicArray);
}

void test_sort_function_name()
{
    DynamicArray* Dynamic_Array = createDynamicArray(100);
    DynamicArray* dynamic_array = createDynamicArray(100);
    Repository* repo = createRepo(*Dynamic_Array);
    Repository* Repository = createRepo(*dynamic_array);
    add_product_repository(Repository, "Apple", "fruit", 50, "01/04/2021");
    add_product_repository(Repository, "Banana", "fruit", 100, "01/04/2022");
    add_product_repository(Repository, "Cake", "sweets", 124, "18/03/2021");
    add_product_repository(Repository, "Cherry", "fruit", 150, "01/04/2022");
    add_product_repository(Repository, "Chicken", "meat", 10, "16/03/2022");
    add_product_repository(Repository, "Chocolate", "sweets", 70, "06/09/2021");
    add_product_repository(Repository, "Cupcake", "sweets", 70, "18/03/2021");
    add_product_repository(Repository, "Milk", "dairy", 3, "16/06/2021");
    add_product_repository(Repository, "Pork", "meat", 40, "16/03/2023");
    add_product_repository(Repository, "Yogurt", "dairy", 5, "16/06/2021");
    init_repo(repo);
    UndoRepository* UndoRedo = createUndoRepo(100, repo);
    Service* service = createService(*repo, *UndoRedo);
    sort_function_Byname(service, repo);
    for (int i = 0; i < Repository->array.count; i++)
    {
        assert(strcmp(Repository->array.data[i].name, repo->array.data[i].name) == 0);
        assert(strcmp(Repository->array.data[i].type, repo->array.data[i].type) == 0);
        assert(Repository->array.data[i].quantity == repo->array.data[i].quantity);
        assert(strcmp(Repository->array.data[i].date, repo->array.data[i].date) == 0);
    }
    destroy_service(service);
    destroy_r(repo);
    destroy_r(Repository);
    destroy_undoRepository(UndoRedo);
    destroy(Dynamic_Array);
    destroy(dynamic_array);
}

void test_sort_function_quantity()
{
    DynamicArray* DynamicArray_ = createDynamicArray(100);
    DynamicArray* da = createDynamicArray(100);
    Repository* repo = createRepo(*DynamicArray_);
    Repository* Repo = createRepo(*da);
    add_product_repository(Repo, "Milk", "dairy", 3, "16/06/2021");
    add_product_repository(Repo, "Yogurt", "dairy", 5, "16/06/2021");
    add_product_repository(Repo, "Chicken", "meat", 10, "16/03/2022");
    add_product_repository(Repo, "Pork", "meat", 40, "16/03/2023");
    add_product_repository(Repo, "Apple", "fruit", 50, "01/04/2021");
    add_product_repository(Repo, "Chocolate", "sweets", 70, "06/09/2021");
    add_product_repository(Repo, "Cupcake", "sweets", 70, "18/03/2021");
    add_product_repository(Repo, "Banana", "fruit", 100, "01/04/2022");
    add_product_repository(Repo, "Cake", "sweets", 124, "18/03/2021");
    add_product_repository(Repo, "Cherry", "fruit", 150, "01/04/2022");
    init_repo(repo);
    UndoRepository* UndoRedoRepositort = createUndoRepo(100, repo);
    Service* service = createService(*repo, *UndoRedoRepositort);
    sort_function_ByQuantity(service, repo);
    for (int i = 0; i < Repo->array.count; i++)
    {
        assert(strcmp(Repo->array.data[i].name, repo->array.data[i].name) == 0);
        assert(strcmp(Repo->array.data[i].type, repo->array.data[i].type) == 0);
        assert(Repo->array.data[i].quantity == repo->array.data[i].quantity);
        assert(strcmp(Repo->array.data[i].date, repo->array.data[i].date) == 0);
    }
    destroy_service(service);
    destroy_r(repo);
    destroy_r(Repo);
    destroy_undoRepository(UndoRedoRepositort);
    destroy(DynamicArray_);
    destroy(da);
}

void test_search_elements_by_string_serviceFunctiom()
{
    DynamicArray* Dynamic_Array = createDynamicArray(100);
    DynamicArray* dynamicarray = createDynamicArray(100);
    Repository* repo = createRepo(*Dynamic_Array);
    Repository* repository = createRepo(*dynamicarray);
    init_repo(repo);
    init_repo(repository);
    int criteria = 1;
    UndoRepository* UndoRedo = createUndoRepo(100, repo);
    Service* service = createService(*repo, *UndoRedo);
    delete_product_repository(repository, "Banana", "fruit");
    delete_product_repository(repository, "Apple", "fruit");
    delete_product_repository(repository, "Chicken", "meat");
    delete_product_repository(repository, "Pork", "meat");
    delete_product_repository(repository, "Chocolate", "sweets");
    delete_product_repository(repository, "Cake", "sweets");
    delete_product_repository(repository, "Milk", "dairy");
    delete_product_repository(repository, "Yogurt", "dairy");
    delete_product_repository(repository, "Cherry", "fruit");
    delete_product_repository(repository, "Cupcake", "sweets");
    search_elements_by_string_service(service, "\n", repository, criteria);
    assert(repository->array.count == 10);
    delete_product_repository(repository, "Banana", "fruit");
    delete_product_repository(repository, "Apple", "fruit");
    delete_product_repository(repository, "Chicken", "meat");
    delete_product_repository(repository, "Pork", "meat");
    delete_product_repository(repository, "Chocolate", "sweets");
    delete_product_repository(repository, "Cake", "sweets");
    delete_product_repository(repository, "Milk", "dairy");
    delete_product_repository(repository, "Yogurt", "dairy");
    delete_product_repository(repository, "Cherry", "fruit");
    delete_product_repository(repository, "Cupcake", "sweets");
    search_elements_by_string_service(service, "C\n", repository, criteria);
    assert(repository->array.count == 5);
    destroy_r(repo);
    destroy_r(repository);
    destroy_service(service);
    destroy_undoRepository(UndoRedo);
    destroy(Dynamic_Array);
    destroy(dynamicarray);

}

void test_copy_repo_service()
{
    DynamicArray* DynamicArray = createDynamicArray(100);
    Repository* repo = createRepo(*DynamicArray);
    init_repo(repo);
    UndoRepository* UndoRedo = createUndoRepo(100, repo);
    Service* service = createService(*repo, *UndoRedo);
    add_product_service(service, "Capsuni", "fruit", 100, "11/12/2022");
    UndoOperation(UndoRedo);
    copy_repo_service(service, UndoRedo);

    assert(strcmp(repo->array.data[0].name, "Banana") == 0);
    assert(strcmp(repo->array.data[0].type, "fruit") == 0);
    assert(repo->array.data[0].quantity == 100);
    assert(strcmp(repo->array.data[0].date, "01/04/2022") == 0);

    destroy_r(repo);
    destroy_service(service);
    destroy_undoRepository(UndoRedo);
    destroy(DynamicArray);
}

void test_createUndoRepo()
{
    DynamicArray* DynamicArray = createDynamicArray(100);
    Repository* repo = createRepo(*DynamicArray);
    init_repo(repo);
    UndoRepository* UndoRedo = createUndoRepo(100, repo);
    assert(UndoRedo->max_length == 100);
    assert(UndoRedo->count == 0);
    assert(UndoRedo->current_position == 0);
    assert(UndoRedo->total_operations == 0);
    destroy_r(repo);
    destroy_undoRepository(UndoRedo);
    destroy(DynamicArray);

}

void test_add_repo_undoRedoRepo()
{
    DynamicArray* DynamicArray = createDynamicArray(100);
    Repository* repo = createRepo(*DynamicArray);
    Repository new_repo;
    init_repo(repo);
    new_repo = duplicate_repository(repo);
    UndoRepository* UndoRedo = createUndoRepo(100, &new_repo);
    Service* service = createService(*repo, *UndoRedo);
    add_product_service(service, "Capsuni", "fruit", 100, "11/12/2022");

    destroy_service(service);
    destroy_undoRepository(UndoRedo);
    destroy_r(repo);
    destroy(DynamicArray);
    free(new_repo.array.data);
}

void test_UndoOperation()
{
    DynamicArray* DynamicArray = createDynamicArray(100);
    Repository* repo = createRepo(*DynamicArray);
    init_repo(repo);
    UndoRepository* UndoRedo = createUndoRepo(100, repo);
    UndoRedo->current_position++;
    assert(UndoOperation(UndoRedo) == 1);
    assert(UndoOperation(UndoRedo) == 0);
    destroy_r(repo);
    destroy(DynamicArray);
    destroy_undoRepository(UndoRedo);
}

void test_RedoOperation()
{
    DynamicArray* Dynamic_Array = createDynamicArray(100);
    Repository* repo = createRepo(*Dynamic_Array);
    init_repo(repo);
    UndoRepository* UndoRedo = createUndoRepo(100, repo);
    UndoRedo->total_operations++;
    assert(RedoOperation(UndoRedo) == 1);
    assert(RedoOperation(UndoRedo) == 0);
    destroy_r(repo);
    destroy_undoRepository(UndoRedo);
    destroy(Dynamic_Array);
}

void test_validate_product_name()
{
    char name[100] = "";
    assert(validate_product_name(name) == 0);
    char name2[100] = "1Ale24";
    assert(validate_product_name(name2) == 0);
    char name3[100] = "Ale";
    assert(validate_product_name(name3) == 1);
}

void test_validate_product_quantity()
{
    char quantity[100] = "";
    assert(validate_product_quantity(quantity) == 0);
    char quantity1[100] = "001";
    assert(validate_product_quantity(quantity1) == 0);
    char quantity2[100] = "100A";
    assert(validate_product_quantity(quantity2) == 0);
    char quantity3[100] = "0";
    assert(validate_product_quantity(quantity3) == 0);
    char quantity4[100] = "123";
    assert(validate_product_quantity(quantity4) == 123);
}

void test_validate_product_type()
{
    char type[100] = "fruit";
    assert(validate_product_type(type) == 1);
    char type1[100] = "fruits";
    assert(validate_product_type(type1) == 0);
}

void test_validate_expiration_date()
{
    char date1[100] = "10.10.11111";
    assert(validate_expiration_date(date1) == 0);
    char date2[100] = "10/10.1111";
    assert(validate_expiration_date(date2) == 0);
    char date3[100] = "10/10/1111";
    assert(validate_expiration_date(date3) == 0);
    char date4[100] = "00/10/2022";
    assert(validate_expiration_date(date4) == 0);
    char date5[100] = "11/00/2022";
    assert(validate_expiration_date(date5) == 0);
    char date6[100] = "11/11/0000";
    assert(validate_expiration_date(date6) == 0);
    char date7[100] = "32/11/2022";
    assert(validate_expiration_date(date7) == 0);
    char date8[100] = "12/13/2022";
    assert(validate_expiration_date(date8) == 0);
    char date9[100] = "12/13/2020";
    assert(validate_expiration_date(date9) == 0);
    char date10[100] = "15/03/2023";
    assert(validate_expiration_date(date10) == 1);
    char date11[100] = "11/A3/20AB";
    assert(validate_expiration_date(date11) == 0);
    char date12[100] = "11/12/2023";
    assert(validate_expiration_date(date12) == 1);
}

void test_validate_criteria()
{
    int criteria = 3;
    assert(validate_criteria(criteria) == 0);
    criteria = 1;
    assert(validate_criteria(criteria) == 1);
}

void testALL()
{
    test_add_repo_undoRedoRepo();
    
    test_update_TElem();
    test_delete_TELEM();
    test_createDynamicArray();
    test_addTElem();
    test_validate_expiration_date();
    test_validate_product_type();
    test_validate_product_quantity();
    test_validate_product_name();
    test_RedoOperation();
    test_UndoOperation();
    test_createUndoRepo();
    test_search_elements_by_string_serviceFunctiom();
    test_sort_function_quantity();
    test_sort_function_name();
    test_duplicate_repo();
    test_check_existence_product();
    test_search_elements_by_category_repository();
    test_search_elements_by_string_repository();
    test_createProduct();
    test_domain_getters();
    test_assign_values();
    test_copy_repo();
    test_update_products_quantity_repository();
    test_getCount();
    test_update_expiration_date_repository();
    test_add_product_r();
    test_delete_product_repository();
    test_createRepo();


    _CrtDumpMemoryLeaks();
    _CrtSetReportMode(_CRT_WARN, _CRTDBG_MODE_FILE);
    _CrtSetReportFile(_CRT_WARN, _CRTDBG_FILE_STDOUT);
    _CrtSetReportMode(_CRT_ERROR, _CRTDBG_MODE_FILE);
    _CrtSetReportFile(_CRT_ERROR, _CRTDBG_FILE_STDOUT);
    _CrtSetReportMode(_CRT_ASSERT, _CRTDBG_MODE_FILE);
    _CrtSetReportFile(_CRT_ASSERT, _CRTDBG_FILE_STDOUT);
}