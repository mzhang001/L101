HOME_FOLDER=/auto/homes/mz342/github/L101
DATA_FOLDER=/auto/homes/mz342/github/L101/data
CODE_FOLDER=${HOME_FOLDER}/slda
i_f=${DATA_FOLDER}/images
O_FOLDER=/auto/homes/mz342/github/L101/output
MODEL_FOLDER=.
T=100
alpha=0.50

#./slda est ${i_f}/train-data.dat ${i_f}/train-label.dat settings.txt $alpha $T ${i_f} ${i_f}
#./slda inf ${i_f}/test-data.dat ${i_f}/test-label.dat settings.txt ${i_f}/final.model ${i_f}  

${CODE_FOLDER}/slda est ${DATA_FOLDER}/traindata ${DATA_FOLDER}/trainlabelFile ${CODE_FOLDER}/settings.txt ${alpha} $T seeded ${MODEL_FOLDER}
