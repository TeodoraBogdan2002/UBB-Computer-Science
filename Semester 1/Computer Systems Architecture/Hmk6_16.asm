bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s1 db 'a','d','n','p'
    l_s1 equ $-s1
    s2 db 'b','c','n','o'
    l_s2 equ $-s2
    
    d resb (l_s1+l_s2)

    ;-----------------Being given two alphabetical ordered strings of characters, s1 and s2, build using merge sort 
    ;                 the ordered string of bytes that contain all characters from s1 and s2.
    
; our code starts here
segment code use32 class=code
    start:
        mov ecx, (l_s1+l_s2)
        
        mov esi,s1 ;we will use esi to go through s1
        mov edx,0 ; we will use edx to go through s2
        mov edi, d ;we will use edi to go through d
        
        jecxz sfarsit
        
        bucla:
            movsb ;we put the current char from s1 in the current index of d
            dec edi ;we decrease edi bc we want to compare the currently stored nr
            
            mov al,[s2+edx] ;we put in AL the current number in s2
            
            scasb ;we compare al with d[last]
            dec edi
            
            jae first_smaller
                ;second smaller
                stosb ;we store al in d
                inc edx ;we will comare tge next element in s2 next time
                dec esi ;we go back to the current index in s1
                jmp end_loop
            first_smaller:
                inc edi ;we increase the current index in d
            end_loop:
            
            cmp esi,[s1+l_s1] ;we check whether we are at the end of s1
            jae out_for
            
            cmp edx,l_s2 ;we check whether we are at the end of s2
            jae out_for
        loop bucla
        
        out_for:
            cmp esi,[s1+l_s1]
            jae second
                ;if we still have elements in the first string, we store them
                ;-else,we store the remaining elements in s2
                mov ecx,s1
                sub ecx,esi
                add ecx,l_s1 ;we compute the number of steps left
                repeta:
                    movsb
                loop repeta
                jmp sfarsit
            second:
                mov ecx, l_s2
                sub ecx, [edx] ;we compute the number of steps left
                repeta2:
                    mov edi,[s2+edx]
                    inc edx
                    inc edi ;we store the remaining elements from s2 in d and increase the indexes
                loop repeta2
                
        sfarsit:
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
