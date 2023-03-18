bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a dw 23
    b dw 5
    c dw 10
    d dw 3

; our code starts here
segment code use32 class=code
    start:
    ;(a+b+b)+(c-d)
        ;mov eax, 0
        mov ax, [a] ; ax<-23
        add ax, [b] ; ax<- 23+5=28
        add ax, [b] ; ax<- 28+5=33
        
        ;mov ebx, 0
        mov bx, [c]; Bx<-10
        sub bx, [d];bx<-10-3=7
        
        add ax, bx ; ax<-ax+bx = 33 + 7 =40
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
