package org.opencompare.traitement;

import org.opencompare.analyse.Analyse;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.ArrayList;

/**
 * Created by Fonck on 07/11/2016.
 */
public interface Traitement {

    /**
     * Retourne la structure de données pour le traitement
     *
     * @return Structure de données de la PCM
     */
    Map getData();

    /**
     * Retourne la structure de données pour l'analyse
     *
     * @return Structure de données de la PCM
     */
    Map getDataInitial();

    /**
     * Enregistre les données de la PCM dans la structure de données
     *
     * @param pcmFile Fichier d'entré au format PCM
     * @return true si la modification est un succès
     */
    boolean setData(File pcmFile);

    /**
     *  Set la structure de données principale
     *
     * @param pcmFile Fichier d'entré au format PCM
     * @throws IOException
     */
    void setDataInitial(File pcmFile) throws IOException;

    /**
     * Retourne les features de la map sous forme de liste pour l'analyse
     *
     * @return ArrayList<String> liste de features
     */
    ArrayList<String> getFeatureListe();

    /**
     * Retourne les produits de la map sous forme de liste pour l'analyse
     *
     * @returnArrayList<String> Liste de produit
     */
    ArrayList<String> getProductListe();

    /**
     * Analyse la PCM pour savoir si il y a des blanc
     * @return true si la case de la pcm est blanche, false sinon
     */
    boolean isBlankPCM();

    /**
     * Supprime les produits inutiles selon un bareme.
     */
    void removeUselessProduct();

    /**
     * Supprime les features inutiles selon un bareme.
     */
    void removeUselessFeatures();
}
