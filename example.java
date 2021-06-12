from sklearn.decomposition import PCA
# TO DO ::: Completar código
# Use las líneas de código que necesite
x = normalised_dataset.values
pca = PCA(n_components = 2)
fit_pca = pca.fit(x)
X_transformed = fit_pca.transform(x)
pca_breast_dataset = pd.DataFrame(X_transformed)
pca_breast_dataset.values


print("Varianza explicada: {}".format(fit_pca.explained_variance_ratio_))


Completar: Se observa que el primer componente principal tiene el ...% de la información, y el segundo solamente el ...%. Al proyectar en un espacio de dimensión 2, se ha perdido aproximadamente un ...% de información.