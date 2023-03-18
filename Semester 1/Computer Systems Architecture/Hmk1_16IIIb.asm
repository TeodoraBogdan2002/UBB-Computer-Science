bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a db 10
    e dw 5
    f dw 23

; our code starts here
segment code use32 class=code
    start:
    ;a*a-(e+f) = 10*10 - (5+23) = 100-28 = 72
        mov ax, 0
        mov al, [a]
        mul byte [a]
        
        mov bx, [e]
        add bx, [f]
        
        sub ax, bx
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
