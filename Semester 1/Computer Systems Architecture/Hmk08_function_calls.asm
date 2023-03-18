bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program

extern exit,printf,scanf     ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import printf msvcrt.dll
import scanf msvcrt.dll

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a dd 20
    b dd 3    messagea  db "a=", 0
    intra db "%d", 0
    messageb  db "b=", 0
    intrb db "%d", 0
    result db "The average of a and b is: %x",0
    helper db "%c", 0
    fractional db ".8",0
    
    res_string resb 10

    
            ;------Read two numbers a and b (in base 10) from the keyboard. 
            ;------Calculate and print their arithmetic average in base 16
; our code starts here
segment code use32 class=code
    start:
    
        ;scanf(%d, &a)
        push messagea
        call [printf]      ; call function printf for printing
        add esp, 4*1
        
        push dword a
        push dword intra
        call [scanf]
        add esp,4*2
        
        ;scanf(%d, &b)
        push messageb
        call [printf]      ; call function printf for printing
        add esp, 4*1
        
        push dword b
        push dword intrb
        call [scanf]
        add esp, 4*2
        
        mov edx, 0
        mov eax,dword [a]
        add eax, dword [b]
        mov ecx, 2
        div ecx ;eax <- edx:eax/ecx=(a+b)/2, edx<-edx:eax%ecx = (a+b)%2
        
        push edx
        
        push dword eax
        push dword result
        call [printf]
        add esp, 4*2 ;we print the quotient in b16
        
        pop edx
        
        cmp edx, 0
        je sf
            push dword fractional
            call [printf]
        sf:
             
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
