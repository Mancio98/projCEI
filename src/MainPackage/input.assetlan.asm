addi sp sp -4
li a0 1
push a0
push fp
li a0 10
push a0
jal function0halt

function0:
move fp sp
push ra
addi sp sp -4
move al fp
lw a0 al0
push a0 /nli a0 0
lw t1 0(sp) /npop /neq t1 a0 a0 /nli t1 1
beq a0 t1label0
push fp
move al fp
lw a0 al0
push a0 /nli a0 1
lw t1 0(sp) /npop /nsub t1 a0 a0 /npush a0
lw al fp 0
lw al al 0
push al
jal function0b label1
label0:
move al fp
lw al al 0
lw a0 al0
print a0
label1:
pop
lw ra sp 0
pop
pop
lw fp sp 0
pop
jr ra
