DATA_FOLDER=/auto/homes/mz342/github/L101/dataTestingSkip
i_f=${DATA_FOLDER}/images
O_FOLDER=/auto/homes/mz342/github/L101/output
MODEL_FOLDER=/auto/homes/mz342/github/L101/model
T=50
alpha=1

#./slda est ${i_f}/train-data.dat ${i_f}/train-label.dat settings.txt $alpha $T ${i_f} ${i_f}
#./slda inf ${i_f}/test-data.dat ${i_f}/test-label.dat settings.txt ${i_f}/final.model ${i_f}  

#./slda est ${DATA_FOLDER}/traindata ${DATA_FOLDER}/trainlabelFile settings.txt $alpha} $T seeded $DATA_FOLDER
#./slda inf ${DATA_FOLDER}/testdata ${DATA_FOLDER}/testlabelFile settings.txt $DATA_FOLDER/final.model $DATA_FOLDER

#./slda inf ${DATA_FOLDER}/traindata ${DATA_FOLDER}/trainlabelFile settings.txt  $DATA_FOLDER/final.model $DATA_FOLDER 

#./slda inf ${DATA_FOLDER}/testdata ${DATA_FOLDER}/testlabelFile settings.txt ${MODEL_FOLDER}/final.model ${O_FOLDER}