bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a db 6
    b db 12
    c db 2

; our code starts here
segment code use32 class=code
    start:
    ;(a+b)/2 + (10-a/c)+b/4 = (18)/2 + 7 + 3 = 19
        mov bx, 0
        mov ax,0
        
        mov al, [a] ; al<-6
        add al, [b] ; al<- 6+12 = 18
        
        mov cl, 2
        div cl ; al<-(a+b)/2=9 ah<-(a+b)%2=0
        
        mov bl, al ;bl<-9
        
        mov ax, 0
        mov al, [a] ; al<-6
        mov cl, [c] ; cl<-2
        div cl ; al<-3=(6/2)
        
        mov cl, 10 ; cl<-10
        sub cl, al ; cl<-10-al = 10-3 =7
        ;mov al,cl ; al<-7
        
        add bl, cl ; bl<-bl+cl=9+7=16
        
        mov ax, 0
        mov al, [b]; al<-12
        mov cl, 4
        div cl ; AL<-3
        
        add bl, al ; bl<-bl+al
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
