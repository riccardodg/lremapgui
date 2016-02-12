/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
public class MapUtility {


    public static String getHashFromString(String str) {
        int hash = 1;

        hash = hash * 31 + str.hashCode();
        return Integer.toString(hash);
    }

    public static TrieMetaData FillTrie(List<String> words, boolean reverse) {
        
       // String prefix = "am", resultW;

//		String[] words = { "amo", "amas", "amat", "amem", "amamus", "amabimus", "laudo",
//				"laudem", "laudas","laudabam","rosam","rosas" };
        //String[] rWords = new String[words.length];
        TrieMetaData rootNode = new TrieMetaData();

        TrieMetaDataNode mytrienode = rootNode.getRoot();
        if (mytrienode == null) {
            mytrienode = new TrieMetaDataNode();
        }

        if (reverse) {
            //prefix = new String(new StringBuilder(prefix).reverse().toString());
            for (String w : words) {
                mytrienode.addWord(new StringBuilder(w).reverse().toString());
            }
        } else {
            for (String w : words) {
                //System.out.println(w);
                mytrienode.addWord(w);
            }
        }
        rootNode.setRoot(mytrienode);
        return rootNode;
    }

    public static List<String> getResultFromTrie(TrieMetaData rootNode, String prefix, boolean reverse) {
        List<String> result = new ArrayList<String>();
        List ret = rootNode.getWords(prefix);
        System.err.println("results -"+rootNode.getRoot().toString()+"-");
        String resultW;
        for (Iterator<String> it = ret.listIterator(); it.hasNext();) {
            resultW = it.next();
            if (reverse) {
                resultW = new StringBuilder(resultW).reverse().toString();
            }
            result.add(resultW);
            System.out.println(resultW);
        }
        System.err.println(rootNode.getRoot().toString());
        return result;
    }

}
