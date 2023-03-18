bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s dd 12345607h, 1A2B3C15h
    len equ ($-s)/4 ; the lenght of the strig in doublewords
    d times len*4 db 0 ;the string in bytes
    len_d equ $-d ; len_d<-8
    

; our code starts here
segment code use32 class=code
    start:
    
    ;Given an array S of doublewords, build the array of bytes D formed from bytes of doublewords sorted as unsigned numbers in descending order.
     ;Example:
       ;s DD 12345607h, 1A2B3C15h
       ;d DB 56h, 3Ch, 34h, 2Bh, 1Ah, 15h, 12h, 07h
       
        mov ecx, len_d ;ecx<-8
        mov esi, s
        mov edi, d
        cld ; go through the strig from left to right(DF=0), from the beginning to its end
        
        jecxz jump_to_sort ;when ecx is 0, we jump to the sorting part
        
        loop_1:
           mov ebx, len_d-1
           movsb ;we copy the strig s into d(store the byte from the adress <DS:ESI> to the adress <ES:ESI>)
           
        loop loop_1
        
        jump_to_sort:        
        
        mov ecx, len_d
        jecxz end_progr
        
        dec ecx ;ecx<-7
        
        
        sort_d:
            mov ebx, ecx ;ecx<-7
            mov esi, 0            
            sort_1:
               mov al, [d+esi]
               mov dl, [d+esi+1]
               cmp al, dl
               jnc noswap ;if CF=0(al>dl) we don't swap the numbers
                 mov [d+esi], dl
                 mov [d+esi+1], al
                 
                noswap:
                    inc esi
                    dec ebx
                    jnz sort_1 ;if ZF=0(the last performed operation was diff from 0) we compare the next numbers
        loop sort_d 
            
            
        
        end_progr:
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
