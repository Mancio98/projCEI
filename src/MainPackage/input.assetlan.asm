addi $sp $sp -1
li $a0 0
push $a0
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
halt
function0:
move $fp $sp
push $ra
move $al $fp
lw $a0 $al 1
push $a0
move $al $fp
lw $al $al 0
lw $a0 $al 3
lw $t1 $sp 0
pop
add $a0 $t1 $a0
sw $a0 $al 3
move $al $fp
li $t1 0
sw $t1 $al 1
move $al $fp
lw $al $al 0
lw $a0 $al 2
push $a0
move $al $fp
lw $al $al 0
lw $a0 $al 2
lw $t1 $sp 0
pop
eq $t1 $a0 $a0
li $t1 1
beq $a0 $t1 label0
move $al $fp
lw $al $al 0
lw $a0 $al 3
print $a0
b label1
label0:
move $al $fp
lw $al $al 0
lw $a0 $al 1
print $a0
label1:
lw $ra $sp 0
pop
pop
pop
lw $fp $sp 0
pop
jr $ra
