#!/usr/bin/env bash

# Ensure this is executable by running 'chmod +x build.sh'
# run with './build.sh'

echo "=============== Building Source Fresh ==============="
echo "=============== Building Source Fresh ===============" > last_run.out
cd ~/pintos/src/userprog/ # change this line for each project

echo "--------------- Cleaning Old Build ---------------"
echo "--------------- Cleaning Old Build ---------------" >> last_run.out
make clean >> last_run.out

echo "--------------- Building Fresh ---------------"
echo "--------------- Building Fresh ---------------" >> last_run.out
make >> last_run.out

echo "=============== Initializing New Disk ==============="
echo "=============== Initializing New Disk ===============" >> last_run.out
cd build/

echo "--------------- Creating New Disk ---------------"
pintos-mkdisk filesys.dsk --filesys-size=2

echo "--------------- Initializing Disk ---------------"
pintos -f -q

echo "--------------- Loading Echo ---------------"
pintos -p ~/pintos/src/examples/echo -a echo -- -q
