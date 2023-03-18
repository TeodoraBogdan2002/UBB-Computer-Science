bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)


            ;16. Given the byte A and the word B, compute the doubleword C as follows:
            ;   the bits 0-7 of C have the value 1
            ;   the bits 8-11 of C are the same as the bits 4-7 of A
            ;   the bits 12-19 are the same as the bits 2-9 of B
            ;   the bits 20-23 are the same as the bits 0-3 of A
            ;   the bits 24-31 are the same as the high byte of B


segment data use32 class=data           ;bits:                          15 14 13 12 | 11 10 9  8 | 7  6  5  4 | 3  2  1  0
    a db 01100011b ;                    ;                                                          0  1  1  0   0  0  1  1   
    b dw 0001110000111001b              ;                                0  0  0  1    1  1 0  0   0  0  1  1   1  0  0  1
    c dd 00000000000000000000000000000000b

; our code starts here
segment code use32 class=code
    start:
        mov ebx, [c]
        or ebx,0000_0000_0000_0000_0000_0000_1111_1111b
        
        ;EBX = 0000 0000 0000 0000 0000 0000 1111 1111B
        
        mov ax,0
        mov eax,0
        mov al,[a]
        and al, 1111_0000b
        rol ax,4
        
        or ebx,eax ;EBX =0000 0000 0000 0000 0000 0110 1111 1111B
        
        mov eax, 0
        mov ax,[b]
        and ax, 0000_0011_1111_1100b
        rol eax,10
        
        or ebx,eax ;0000 0000 0000 0000 1110 0110 1111 1111B
        
        mov eax, 0
        mov ax,0
        mov al,[a]
        and al,00001111b ;  eax =0000 0000 0000 0000 | 0000 0000 0000 0011
        ror eax,12
        
        or ebx,eax    ;0000 0000 0011 0000 | 1110 0110 1111 1111
        
        mov eax,0
        mov ax,0
        mov ax,[b]
        and ax,1111_1111_0000_0000b  ; eax = 0000 0000 0000 0000 | 0001 1100 0000 0000
        rol eax,16
        
        or ebx,eax
        
        
                    ;0001 1100 0011 0000 | 1110 0110 1111 1111
                    ;0001 1100 0011 0000 | 1110 0110 1111 1111
        
        
        
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
