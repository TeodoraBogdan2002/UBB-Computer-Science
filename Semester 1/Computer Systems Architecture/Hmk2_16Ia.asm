bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a db 25
    b dw 12
    c dd 37

; our code starts here
segment code use32 class=code
    start:
    ;c-a-(b+a) + c    UNSIGNED REP.
        ;mov eax, 0 ; convert to doubleword
        
        mov eax, [c]; EAX<-37
        mov ebx,0; convert to doubleword
        mov bl, [a] ; BL<-byte a .....ebx = a
        sub eax,ebx ; Eax<-eax-ebx = c-a = 12
        mov ebx, eax
        
        mov ax,0 ;convert to word
        mov eax, 0;
        mov al, [a] ;al<-byte a ... AX=a
        add ax, [b] ;Ax<-Ax+b = a+b = 37 ...eax= 37
        
        sub ebx, eax ;EBX =EBX-EAX = 12-37 = -25
        
        add ebx, [c] ;EBX=EBX+c = 12
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
