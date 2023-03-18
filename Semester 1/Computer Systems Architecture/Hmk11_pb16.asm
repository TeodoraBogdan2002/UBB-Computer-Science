bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, fprintf, scanf,fopen, fclose,printf               

import exit msvcrt.dll  
import printf msvcrt.dll  
import fprintf msvcrt.dll  
import scanf msvcrt.dll  
import fopen msvcrt.dll  
import fclose msvcrt.dll  

extern MaxNr
; MaxNr(int) -> int
    ; - 2 parameters : x(integer), max(integer)
    ; - checks whther x is larger than the number stored in max
    ; - return value in ebx( integer )
    ; - uses/modifies edx, ebx
    ; - procedure will free stack parameters



; our data is declared here (the variables needed by our program)
segment data use32 class=data
    len dd 0
    number dd 0
    max dd 0
    
    read_str db '%s', 0
    read_int db '%d', 0
    print_chr db '%X', 0
    mesaj db 'error', 0
    
    w_fd dd -1
    w_acc_mode db "w", 0
    w_file_name db "output.txt", 0

; our code starts here
segment code use32 class=code
    start:
        ; Read a string of unsigned numbers in base 10 from keyboard. Determine the maximum value of the string and write it in the file max.txt (it will be created) in 16  base.
        
        ; scanf( '%d', len) -> how many numbers we input
        push dword len
        push dword read_int
        call [scanf]
        add esp, 4*2
        
        mov ecx,[len] ;we save the number of numbers to be read in ecx
        
        read_loop:
            push ecx ;put ecx on the stack so we don't lose its value
            
            ;scanf('%c', number) ->we read the numbers from the console
            push dword number
            push dword read_int
            call [scanf]
            add esp, 4*2
            
            ;we call MaxNr module, which will check whether the current number is the maximum one
            push dword [max]
            push dword [number]
            call MaxNr
            
            mov [max], ebx ;we save the value from ebx in max
            pop ecx ;we retreive ecx from the stack
            
        loop read_loop
        
        ; eax= fopen("output.txt", "w")
        push dword w_acc_mode
        push dword w_file_name
        call [fopen]
        add esp, 4*2
        
        ; fd=eax -> we jump to the end if there is an error while opening file
        cmp eax, 0
        je sfarsit
        
        ;we save the file descriptor from eax in w_fd
        mov [w_fd], eax
        
        ;fprinf(w_file_descriptor, '%s', ebx)
        push dword [max]
        push dword print_chr
        push dword [w_fd]
        call [fprintf]
        add esp, 4*3
        
            
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
