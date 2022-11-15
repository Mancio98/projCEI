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
jal function2
pop 
pop 
halt
function0:
move $fp $sp
push $ra
li $a0 1
lw $ra $sp 0
pop
pop
pop
lw $fp $sp 0
pop
jr $ra

function1:
move $fp $sp
push $ra
addi $sp $sp -1
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
li $a0 1
li $t1 1
beq $a0 $t1 label0
li $a0 1
li $t1 1
beq $a0 $t1 label2
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
move $al $fp
li $t1 0
sw $t1 $al 1
move $al $fp
li $t1 0
sw $t1 $al 3
move $al $fp
li $t1 0
sw $t1 $al 2
jal function1
move $al $fp
sw $a0 $al 4
b label3
label2:
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
move $al $fp
li $t1 0
sw $t1 $al 1
move $al $fp
li $t1 0
sw $t1 $al 3
move $al $fp
li $t1 0
sw $t1 $al 2
jal function1
move $al $fp
sw $a0 $al 4
label3:
b label1
label0:
li $a0 1
li $t1 1
beq $a0 $t1 label4
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
move $al $fp
li $t1 0
sw $t1 $al 1
move $al $fp
li $t1 0
sw $t1 $al 3
move $al $fp
li $t1 0
sw $t1 $al 2
jal function1
move $al $fp
sw $a0 $al 4
b label5
label4:
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
move $al $fp
li $t1 0
sw $t1 $al 1
move $al $fp
li $t1 0
sw $t1 $al 3
move $al $fp
li $t1 0
sw $t1 $al 2
jal function1
move $al $fp
sw $a0 $al 4
label5:
label1:
li $a0 1
pop
lw $ra $sp 0
pop
pop
pop
pop
pop
lw $fp $sp 0
pop
jr $ra

function2:
move $fp $sp
push $ra
addi $sp $sp -1
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
move $al $fp
li $t1 0
sw $t1 $al 3
move $al $fp
li $t1 0
sw $t1 $al 2
move $al $fp
li $t1 0
sw $t1 $al 1
jal function1
move $al $fp
sw $a0 $al 4
move $al $fp
lw $al $al 0
addi $al $al 2
move $a0 $al
transf $a0
pop
lw $ra $sp 0
pop
pop
pop
pop
pop
lw $fp $sp 0
pop
jr $ra
