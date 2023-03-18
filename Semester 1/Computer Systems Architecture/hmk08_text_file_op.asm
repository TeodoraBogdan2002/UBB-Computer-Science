bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, fread, printf, fopen, fclose               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import fread msvcrt.dll
import printf msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll

; our data is declared here (the variables needed by our program)

                 ;A text file is given. Read the content of the file, count the number of letters 'y' and 'z' 
                 ;and display the values on the screen. The file name is defined in the data segment.

segment data use32 class=data
    fd dd -1
    acc_mode db "r",0
    file_name db "hmk08input.txt", 0
    
    len equ 5
    buffer resb len
    nr_read_chars dd 0
    
    nr_y dd 0
    nr_z dd 0
    
    print_msg db "Nr. of y: %d and nr. of z: %d", 0

; our code starts here
segment code use32 class=code
    start:
        ; eax = fopen("hmk08input.txt","r")
        push dword acc_mode
        push dword file_name
        call [fopen]
        add esp, 4*2
        
        ; fd = eax
        cmp eax, 0
        je sfarsit
        
        ;we save the file descriptor from eax in fd
        mov [fd],eax
        
        ;we read all the chars in input.txt using a buffer
        
        read:
            ; eax = fread(buffer, 1, len, file_descriptor)
            push dword [fd]
            push dword len
            push dword 1 ;we get 1 byte of length len
            push dword buffer
            call [fread]
            add esp, 4*4
            
            cmp eax, 0
            je out_read ;if we don't have anythong else to read, we exit the loop
            
            mov [nr_read_chars], eax ;we save the number of chars we read 
            
            mov ebx, 0
            
            count_letters:
                ;we check wether the current char is 'y'
                cmp [buffer+ebx], byte 'y'
                jne continue
                    ; if it is y, we increase the nr of 'y'-save
                    add [nr_y], byte 1
                continue:
                
                ;we check whether the current char is 'z'
                cmp [buffer+ebx], byte 'z'
                jne continue1
                    ; if it is z, the current char is 'z'
                    add [nr_z], byte 1
                continue1:
                inc ebx ;we go to the next character
                cmp ebx, [nr_read_chars]
                je out_count ;if we are at the end, we exit this loop
                
            jmp count_letters
            
            out_count:
            
        jmp read
            
        out_read:
        
        push dword [nr_z]
        push dword [nr_y]
        
        push dword print_msg
        call [printf]
        add esp, 4*3
        
        push dword [fd]
        call [fclose]
        add esp, 4
        
        sfarsit:
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
