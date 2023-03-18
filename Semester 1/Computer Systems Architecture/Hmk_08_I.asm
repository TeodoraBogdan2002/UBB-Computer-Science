bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, printf, scanf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll 
import printf msvcrt.dll
import scanf msvcrt.dll
   ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions


; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a dd 14
    b dd 7
    formatscana db "%d", 0
    formatscanb db "%d", 0
    suma db "The sum of a and b is: %x", 0 ;we will reprezent the number/sum in hexadecimal
    
;41000 + 804 =A34C
; our code starts here
segment code use32 class=code
    start:
    
    ;Read two numbers a and b (in base 10) from the keyboard and calculate a+b. Display the result in base 16
    
    
        ;scanf("%d", &a)
        push dword a
        push dword formatscana
        call [scanf] ; call scanf for reading
        add esp, 4*2 ; taking parameters out of the stack; 4 = dimension of a dword; 2 = nr of parameters
        
        ;scanf("%d", &b)
        push dword b
        push dword formatscanb
        call [scanf] ;; call scanf for reading
        add esp, 4*2 ; taking parameters out of the stack; 4 = dimension of a dword; 2 = nr of parameters
        
        mov eax, dword [a] ; EAX<-a
        add eax, dword [b] ; EAX<-EAX+b=a+b
        
        
        ;printf("The sum of a and b is: %d \n", sum)
        push dword eax
        push dword suma
        call [printf] ; call function printf for printing
        add esp, 4*1 ; free parameters on the stack
        
        ;pop edx
    
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
