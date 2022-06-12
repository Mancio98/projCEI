addi $sp $sp -1
addi $sp $sp -1
move $fp $sp
li $t1 1
sub $fp $t1 $fp
push $fp
li $a0 3
push $a0
li $a0 2
push $a0
li $a0 1
push $a0
lw $al $fp 0
push $al
jal function1
pop 
pop 
halt
function0:
move $fp $sp
push $ra
push $fp
move $al $fp
lw $a0 $al 1
push $a0
move $al $fp
lw $a0 $al 3
push $a0
move $al $fp
lw $a0 $al 2
push $a0
lw $al $fp 0
lw $al $al 0
push $al
jal function0
move $al $fp
lw $a0 $al 1
push $a0
move $al $fp
lw $al $al 0
lw $a0 $al 2
lw $t1 $sp 0
pop
add $a0 $t1 $a0
sw $a0 $al 2
move $al $fp
li $t1 0
sw $t1 $al 1
lw $ra $sp 0
pop
pop
pop
pop
pop
lw $fp $sp 0
pop
jr $ra

function1:
move $fp $sp
push $ra
push $fp
move $al $fp
lw $a0 $al 3
push $a0
move $al $fp
lw $a0 $al 2
push $a0
move $al $fp
lw $a0 $al 1
push $a0
lw $al $fp 0
lw $al $al 0
push $al
jal function0
move $al $fp
lw $al $al 0
addi $al $al 2
move $a0 $al
transf $a0
lw $ra $sp 0
pop
pop
pop
pop
pop
lw $fp $sp 0
pop
jr $ra
