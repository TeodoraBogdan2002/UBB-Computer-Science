bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)

    
    ;Two character strings S1 and S2 are given. Obtain the string D by concatenating the elements found on odd positions in S2 and the elements found on even positions in S1.
    ; S1: 'a', 'b', 'c', 'b', 'e', 'f'
    ; S2: '1', '2', '3', '4', '5'
    ; D: '1', '3', '5', 'b', 'b', 'f'

segment data use32 class=data
    S1 db 'a','b','c','d','e','f'
    ls1 equ $-S1
    S2 db '1','2','3','4','5'
    ls2 equ $-S2
    D times (ls1+ls2) db 0

; our code starts here
segment code use32 class=code
    start:
        mov eax, ls2
        ;mov edx, ls2
        mov edx,0
        
        mov ebx,2
        div ebx ; EAX=EDX:EAX div BX, EDX = EDX:EAX mod BX
        add eax,edx ;add the remainder if it exists
        
        mov ecx, eax ;save the number of steps
        
        mov edi, 0 ;we will use this to go through d
        mov esi, 0 ;we will use this to go through s2
        
        jecxz next
        for_oddp:
            mov bl,[S2+esi]
            mov [D+edi], bl ;take each element on odd positions in s2 and put them in d
            inc edi
            inc esi
            inc esi
        loop for_oddp
        
        next:
        
        mov eax,ls1
        mov edx,0
        
        mov ebx,2
        div ebx ;eax= edx:eax div bx,  edx= edx:eax mod bx
        add eax,edx ; add the remainder if it exists
        
        mov ecx, eax ; save the number of steps
        
        mov esi,1 ;we will use this to go through s1
        
        jecxz endd
        
        for_evenp:
            mov BL, [S1+esi]
            mov [D+EDI],BL ;take each element on even positions in s1 and put them in d
            inc EDI
            inc ESI
            inc ESI
        loop for_evenp
        
        endd:
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
