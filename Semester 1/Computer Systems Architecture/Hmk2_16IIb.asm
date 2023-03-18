bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    x dq 100
    a dw 45
    b db 5
    c dw 1
    d db 2
    e dd 8
    n dd 3

; our code starts here
segment code use32 class=code
    start:
    ;x/2+100*(a+b)-3/(c+d)+e*e      SIGNED REP.
        mov eax, [x] ;eax=100
        mov edx, [x+4] ;edx =0
        mov ebx, 2 ;ebx= 2
        idiv ebx ;EAX ← EDX:EAX / EBX = 50, EDX ← EDX:EAX % EBX = 0
        
        mov ebx, eax
        
       

        mov al, [b] ;Al=byte b ....ax=b
        cbw
        
        add ax, [a]; AX<-b+a= 45+5 = 50
        mov dx, 100
        imul dx ; DX:AX<- 50*100 = 5000 = 0000 1388h....Dx=0000 Al=1388
        push dx
        push ax
        pop eax
        
        ;cwde
        
        add eax, ebx ;EAX = 100*(a+b)+ x/2 = 5050
        
        mov ebx, eax ;EBX = 100*(a+b)+ x/2 = 5050
        
        ;mov eax, 0
        
        ;mov cx, 0
        mov Al, [d] ; CL=2....CX=2
        cbw
        add Ax, word [c] ; CX=CX+c=2+1 = 3
        mov si ,Ax
        
        mov dx, [n+2]
        mov ax, [n]
        cwde
        
        idiv SI ; Ax<-DX:AX/cx = 1  DX<-DX:AX%CX =0.....eax = 1
        
        sub ebx, eax ;EBX= x/2+100*(a+b)-3/(c+d) = 5049
        
        mov eax, [e]
        mov ecx, [e]
        imul ecx ; EDX:EAX ← EAX * e .... edx = 0  EAX =64
        
        add ebx, eax; x/2+100*(a+b)-3/(c+d)+e*e = 5049+64 = 5113
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
