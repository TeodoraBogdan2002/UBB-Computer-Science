
bits 32

global start

; declare external functions needed by our program
extern exit, fopen, fprintf, fclose
import exit msvcrt.dll
import fopen msvcrt.dll
import fprintf msvcrt.dll
import fclose msvcrt.dll

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    file_name db "hmk_text.txt", 0   ; filename to be created
    access_mode db "w", 0       ; file access mode:
                                ; w - creates an empty file for writing
    file_descriptor dd -1       ; variable to hold the file descriptor
    text db "An@$#a ar&e m@e#re.", 0  ; text to be write to file
    len equ $-text-1
    ;s dd '@', '#', '$', '&', '*'
    ;len_s equ $-s-1
    
    ;A file name and a text (defined in the data segment) are given. The text contains lowercase letters, uppercase letters, digits and special characters. Replace all the special characters from the given text with the character 'X'. Create a file with the given name and write the generated text to file.

; our code starts here
segment code use32 class=code
    start:
    
        mov edi, text
        mov ecx, len
        jecxz final
        replace_chars:
            cmp byte [edi], '@'
            jne not_special1
            mov byte [edi], 'X'
            
            not_special1:
            
            cmp byte [edi], '#'
            jne not_special2
            mov byte [edi], 'X'
            
            not_special2:
            
            cmp byte [edi], '$'
            jne not_special3
            mov byte [edi], 'X'
            
            not_special3:
            
            cmp byte [edi], '&'
            jne not_special4
            mov byte [edi], 'X'
            
            not_special4:
            
            cmp byte [edi], '*'
            jne not_special5
            mov byte [edi], 'X'
            
            not_special5:
            inc edi
        loop replace_chars
            
       
        
        ; call fopen() to create the file
        ; fopen() will return a file descriptor in the EAX or 0 in case of error
        ; eax = fopen(file_name, access_mode)
        push dword access_mode     
        push dword file_name
        call [fopen]
        add esp, 4*2                ; clean-up the stack

        mov [file_descriptor], eax  ; store the file descriptor returned by fopen
        
        ; check if fopen() has successfully created the file (EAX != 0)
        cmp eax, 0
        je final

        ; write the text to file using fprintf()
        ; fprintf(file_descriptor, text)
        push dword text
        push dword [file_descriptor]
        call [fprintf]
        add esp, 4*2

        ; call fclose() to close the file
        ; fclose(file_descriptor)
        push dword [file_descriptor]
        call [fclose]
        add esp, 4

      final:

        ; exit(0)
        push dword 0      
        call [exit]