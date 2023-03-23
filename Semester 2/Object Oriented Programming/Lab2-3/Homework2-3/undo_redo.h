#pragma once
#include "repo.h"
#pragma once

typedef struct
{
    Repository* data;
    int count;
    int max_length;
    int current_position;
    int total_operations;

}UndoRepository;


/// With this function we create the repository for undo redo
/// \param capacity represents the capacity of the repository
/// \param repo represents the repository which contains the array of products
/// \return returns the new created repo for undo redo operations
UndoRepository* createUndoRepo(int capacity, Repository* repo);

/// With this function we destroy the repository for undo redo
/// \param UndoRedo represents the repository
void destroy_undoRepository(UndoRepository* UndoRedo);

/// With this function we increase the size of the repository
/// \param U represents the repository
void resize_undoRepository(UndoRepository* U);

/// With this function we add a new repository to our bigger repository
/// \param U represents the undo redo repository
/// \param repo represents the repository we are about to add to our undo redo repo
void add_repo_undoRepository(UndoRepository* U, Repository* repo);

/// With this function we execute a redo operation
/// \param UndoRedo represents the undo redo repository
/// \return it returns 0 if there are no redo possible and 1 if there took place a redo
int RedoOperation(UndoRepository* UndoRedo);

/// With this function we execute an undo operation
/// \param UndoRedo represents the undo redo repository
/// \return it returns 0 if there are no undo possible and 1 if there took place an undo
int UndoOperation(UndoRepository* UndoRedo);