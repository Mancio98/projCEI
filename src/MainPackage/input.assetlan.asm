li $a0 0
push $a0
move $fp $sp
li $t1 1
sub $fp $t1 $fp
push $fp
lw $al $fp 0
push $al
jal function1
pop 
halt
function0:
move $fp $sp
push $ra
li $a0 6
lw $ra $sp 0
pop
pop
lw $fp $sp 0
pop
jr $ra

function1:
move $fp $sp
push $ra
push $fp
lw $al $fp 0
lw $al $al 0
push $al
jal function0
move $al $fp
lw $al $al 0
sw $a0 $al 1
move $al $fp
lw $al $al 0
lw $a0 $al 1
print $a0 1
lw $ra $sp 0
pop
pop
lw $fp $sp 0
pop
jr $ra
