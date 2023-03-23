#include "controller.h"
#include "undo_redo.h"
#include "repo.h"
#include "ui.h"
#include <stdio.h>
#include <time.h>
#include <crtdbg.h>
#include <string.h>
#include "tests.h"
#include <stdlib.h>

int main()
{
    DynamicArray* DynamicArray = createDynamicArray(100);
    Repository* repo = createRepo(*DynamicArray);
    Repository new_repository;
    init_repo(repo);
    new_repository = duplicate_repository(repo);
    UndoRepository* UndoRedo = createUndoRepo(100, &new_repository);
    //UndoRepo* UndoRedo = createUndoRepo(100, repo);

    Service* service = createService(*repo, *UndoRedo);
    Console* console = createConsole(*service);
    start(console);
    //free(repo->data);
    destroy_ui(console);
    destroy_service(service);
    destroy_undoRepository(UndoRedo);
    destroy_r(repo);
    destroy(DynamicArray);
    
    free(new_repository.array.data);


    testALL();
    _CrtDumpMemoryLeaks();
    _CrtSetReportMode(_CRT_WARN, _CRTDBG_MODE_FILE);
    _CrtSetReportFile(_CRT_WARN, _CRTDBG_FILE_STDOUT);
    _CrtSetReportMode(_CRT_ERROR, _CRTDBG_MODE_FILE);
    _CrtSetReportFile(_CRT_ERROR, _CRTDBG_FILE_STDOUT);
    _CrtSetReportMode(_CRT_ASSERT, _CRTDBG_MODE_FILE);
    _CrtSetReportFile(_CRT_ASSERT, _CRTDBG_FILE_STDOUT);



    return 0;
}