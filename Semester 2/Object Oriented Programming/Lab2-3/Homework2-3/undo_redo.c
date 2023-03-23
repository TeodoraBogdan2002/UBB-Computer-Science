
#include "repo.h"
#include "undo_redo.h"
#include <stdio.h>
#include <stdlib.h>

UndoRepository* createUndoRepo(int capacity, Repository* repo)
{
    UndoRepository* UndoRedoRepository = (UndoRepository*)malloc(sizeof(UndoRepository));
    //make sure that the space was allocated
    if (UndoRedoRepository == NULL)
        return NULL;
    UndoRedoRepository->count = 0;
    UndoRedoRepository->max_length = capacity;
    UndoRedoRepository->current_position = 0;
    UndoRedoRepository->total_operations = 0;

    //allocate space for the elements
    UndoRedoRepository->data = (Repository*)malloc(capacity * sizeof(Repository));
    if (UndoRedoRepository->data == NULL)
        return NULL;

    UndoRedoRepository->data[UndoRedoRepository->current_position] = duplicate_repository(repo);
    //U->data[U->current_position] = *repo;

    //getData(repo, &U->data[U->current_position]);

//    U->data[U->current_position].count = repo->count;
//    U->data[U->current_position].max_length = repo->max_length;
//    U->data[U->current_position] = *repo;

    return UndoRedoRepository;
}

void destroy_undoRepository(UndoRepository* UndoRepository)
{

    if (UndoRepository == NULL)
        return;

    //free the space allocated for the repositories
//    for(int j = 0; j< U->count; j++)
//        for(int i = 0; i < U->data[j].count; i++)
//            free(&U->data[i]);

    for (int i = 0; i <= UndoRepository->count; i++)
        free(UndoRepository->data[i].array.data);

    free(UndoRepository->data);
    UndoRepository->data = NULL;

    //free the space allocated for the dynamic array
    free(UndoRepository);
}

void resize_undoRepository(UndoRepository* UndoRepository)
{
    UndoRepository->data = (Repository*)(realloc(UndoRepository, sizeof(Repository) * UndoRepository->max_length * 2));
    UndoRepository->max_length = UndoRepository->max_length * 2;
}

void add_repo_undoRepository(UndoRepository* UndoRepository, Repository* repository)
{
    if (UndoRepository == NULL)
        return;
    if (UndoRepository->data == NULL)
        return;

    //resize the array, if necessary
    if (UndoRepository->max_length == UndoRepository->current_position)
        resize_undoRepository(UndoRepository);

    UndoRepository->current_position++;
    UndoRepository->count++;
    UndoRepository->total_operations = UndoRepository->current_position;
    //    U->data[U->current_position].count = repo->count;
    //    U->data[U->current_position].max_length = repo->max_length;
        //U->data[U->current_position] = *repo;
    UndoRepository->data[UndoRepository->current_position] = duplicate_repository(repository);
    //getData(repo, &U->data[U->current_position]);

}

int RedoOperation(UndoRepository* UndoRedo)
{
    if (UndoRedo->current_position == UndoRedo->total_operations)
        return 0;

    UndoRedo->current_position++;
    return 1;

}

int UndoOperation(UndoRepository* UndoRedo)
{
    if (UndoRedo->current_position == 0)
        return 0;

    UndoRedo->current_position--;
    return 1;
}

//void delete_repo_ur(UndoRepo* U)
//{
//    for(int i = U->count-1; i < U->count; i++)
//        U->data[i] = U->data[i+1];
//    U->count--;
//}