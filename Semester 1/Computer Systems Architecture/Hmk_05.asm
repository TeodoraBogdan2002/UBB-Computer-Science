bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a db 2, 1, 3, 3, 4, 2, 6
    l_a equ $-a; l_a<- length of a
    b db 4, 5, 7, 6, 2, 1
    l_b equ $-b ; l_b<- length of b
    
    r resb l_a + l_b ; we reserve l_a+l_b bytes 

; our code starts here
segment code use32 class=code
    start:
    
    
    ;Two byte strings A and B are given. Obtain the string R by concatenating the elements of B in reverse order and the odd elements of A.
    ;Example:
    ;        A: 2, 1, 3, 3, 4, 2, 6
    ;        B: 4, 5, 7, 6, 2, 1
    ;        R: 1, 2, 6, 7, 5, 4, 1, 3, 3
        
    mov edi, 0 ; initialize string adress index for R
    mov ECX, l_b; ecx<- length of B
    
    jecxz jump_to_a ;jump to A if ECX is 0=nothing remained
    
    mov ESI, 0 ; esi<-0 our index for the byte strings
    
    elem_ofB:
        mov ebx, l_b
        sub ebx, 1
        sub ebx, esi ; we go down step by step from the fifth position
        
        mov al, [b+ebx] ; i put ech element in al
        mov [r+edi], al ;i put the elements of al in R
        inc edi ;increment the string adress index for r
        
        inc esi ; increments the index
    loop elem_ofB
    
    jump_to_a:
    mov ecx, l_a ; ecx<- length of a
    jecxz end_progr
    
    mov esi, 0
    elem_ofA:
        mov al, [a+esi]
        test al, 001 ;we verify if the lowest bit is 1=odd number
        
        jz end_loop ;if AL is even jump to the end_loop lebel(ZF=1)
        
          mov [r+edi], al ;i put the elements of al in R
          inc edi ;increment the string adress index for r
        
        end_loop:
        inc esi ; increments the index
    loop elem_ofA
    
    end_progr:
    
    
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
