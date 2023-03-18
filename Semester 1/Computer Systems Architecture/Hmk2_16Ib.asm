bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; SIGNED REPRESENTATION
    a db -9
    c dd 27
    d dq 20

; our code starts here
segment code use32 class=code
    start:
    ;(d-a)-(a-c)-d = (20+9)-(-9-27)-20=29+36-20 = 45   SIGNED REP.
        
        mov eax, [d] ; EAX = 20
        mov edx, [d+4] ; EDX=0
        mov ebx, eax
        
        mov al, [a] ; aL=-9
        
        cbw ; AX = -9
        cwde ; EAX = -9
        
        sub ebx, eax ; EbX<-EBX-EAX = 20 - (-9) = 29
        sbb edx, 0
        
        mov ecx, ebx ;ecx= 29
        
        mov al, [a] ; AL= -9
        cbw  ;AX = -9
        cwde ; EAX= -9
        sub eax, [c] ; eax=-36
        
        mov ebx, eax ; EBX=-36 
        
        mov eax, ecx ; EAX= 29
        
        sub eax, ebx ; EAX = EAX- EBX= 29 - (-36) = 65
        
        mov ebx, eax ;EBX=65
        
        
        mov eax, [d] ;eax=20
        mov edx, [d+4]     
        
        ;xchg eax, ebx ; eax = 64   ebx=20
        
        sub ebx, eax;eax<-eax-ebx = 65-20 = 45
        ;sbb edx, 0
        ;sbb edx, ecx
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
