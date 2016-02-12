/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.ilc.utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Riccardo Del Gratta &lt;riccardo.delgratta@ilc.cnr.it&gt;
 */
public class TrieMetaData {
	private TrieMetaDataNode root;

	public TrieMetaData(TrieMetaDataNode root) {
		super();
		this.root = root;
	}

	public TrieMetaDataNode getRoot() {
		return root;
	}

	public void setRoot(TrieMetaDataNode root) {
		this.root = root;
	}

	public TrieMetaData() {
		root = new TrieMetaDataNode();
	}

	/**
	 * Adds a word to the Trie
	 * 
	 * @param word
	 */
	public void addWord(String word) {
		root.addWord(word.toLowerCase());
	}
	
	/**
	    * Get the words in the Trie with the given
	    * prefix
	    * @param prefix
	    * @return a List containing String objects containing the words in
	    *         the Trie with the given prefix.
	    */
	   public List<String> getWords(String prefix)
	   {
	      //Find the node which represents the last letter of the prefix
	      TrieMetaDataNode lastNode = root;
	      for (int i=0; i<prefix.length(); i++)
	      {
	      lastNode = lastNode.getNode(prefix.charAt(i));
	      
	      //If no node matches, then no words exist, return empty list
	      if (lastNode == null) return new ArrayList<String>();      
	      }
	      
	      //Return the words which eminate from the last node
	      return lastNode.getWords();
	   }

}
