bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a db 2
    b db 10
    c db 7
    d db 14

; our code starts here
segment code use32 class=code
    start:
    ;a+13-c+d-7+b
        mov eax, 0
        mov al, [a] ; Al<-2
        add al, 13 ; Al<-2+13 = 15
        sub al, [c]; Al<-15-7=8
        add al, [d] ; Al<-8+14 = 22
        sub al, 7 ; AL<-22-7 = 15
        add al, [b]; AL<-15+10=25
        
        
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
