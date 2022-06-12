li $a0 1
push $a0
move $fp $sp
li $t1 1
sub $fp $t1 $fp
push $fp
li $a0 10
push $a0
lw $al $fp 0
push $al
jal function0
pop 
halt
function0:
move $fp $sp
push $ra
move $al $fp
lw $a0 $al 1
push $a0
li $a0 0
lw $t1 $sp 0
pop
eq $t1 $a0 $a0
li $t1 1
beq $a0 $t1 label0
move $al $fp
lw $al $al 0
lw $a0 $al 1
push $a0
move $al $fp
lw $a0 $al 1
lw $t1 $sp 0
pop
mult $t1 $a0 $a0
move $al $fp
lw $al $al 0
sw $a0 $al 1
push $fp
move $al $fp
lw $a0 $al 1
push $a0
li $a0 1
lw $t1 $sp 0
pop
sub $t1 $a0 $a0 
push $a0
lw $al $fp 0
lw $al $al 0
push $al
jal function0
b label1
label0:
move $al $fp
lw $al $al 0
lw $a0 $al 1
print $a0 1
label1:
lw $ra $sp 0
pop
pop
pop
lw $fp $sp 0
pop
jr $ra
