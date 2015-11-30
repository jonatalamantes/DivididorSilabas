package Silabeador;

import java.util.*;

public class Silabeador //Clase Estática
{    
    /***********************           Funciones de Silabas   ************************************/
   
    /* Constantes para vocales */
    
    /**
     * Función para dividir una palabra a sílabas usando gramáticas regulares
     * 
     * @author   Jonathan Sandoval   <jonathan_s_pisis@yahoo.com.mx>
     * @param    String              La palabra del idioma español a ser tranformada a Silabas.
     * @return   LinkedList<String>  Lista con las silabas de esa palabra.
     */
    public static LinkedList<String> palabraASilabas(String palabraRecibida)
    {
        LinkedList<String> lista = new LinkedList<String>();
        LinkedList<String> otra = new LinkedList<String>();

        String palabra = palabraRecibida.toLowerCase();

        //revisamos que no inicie con sub o post
        while (palabra.length() >= 3)
        {
            if (palabra.length() > 3)
            {
                if ("post".equals(palabra.substring(0,4)))
                {
                    otra.add(0, palabra.substring(0,4));
                    palabra = palabra.substring(4);
                }
                else if ("sub".equals(palabra.substring(0,3)))
                {
                    otra.add(0, palabra.substring(0,3));
                    palabra = palabra.substring(4);
                }
                else
                {
                    break;
                }
            }
            else
            {
               if ("sub".equals(palabra.substring(0,3)))
                {
                    otra.add(0, palabra.substring(0,3));
                    palabra = palabra.substring(4);
                }
                else
                {
                    break;
                } 
            }
        }

        //invertimos la palabra
        palabra = invertirPalabra(palabra);
        
        //y la transformamos a una palabra con acentos
        while (!"".equals(palabra))
        {
            int cantidadVocal      = iniciaVocal(palabra);
            int cantidadConsonante = iniciaConsonante(palabra);
            int cantidadDigito     = esDigito(palabra);
            int cantidadTotal;

            if (cantidadVocal > 0) //inicia con vocal
            {            	
                if (palabra.length() > 1) //Vocal + Consonante
                {
                    cantidadConsonante = iniciaConsonante(palabra.substring(cantidadVocal));
                    cantidadTotal      = cantidadConsonante + cantidadVocal;
                }
                else //Vocal
                {
                    cantidadTotal = 1;
                }
            }
            else if (cantidadConsonante > 0) //inicia con consonante
            {
                if (palabra.length() > 1 || cantidadConsonante < palabra.length())
                {
                    int otro;
                    cantidadVocal = iniciaVocal(palabra.substring(cantidadConsonante));
                                        
                    if (cantidadVocal > 0) //sigue una vocal
                    {
                        if (palabra.length() > 2) //Consonante + Vocal + Consonante
                        {
                            otro = cantidadVocal + cantidadConsonante;
                            cantidadTotal = otro + iniciaConsonante(palabra.substring(otro));
                        }
                        else //Consonante + Vocal
                        {
                            cantidadTotal = cantidadConsonante + cantidadVocal;
                        }
                    }
                    else //sigue una consonante
                    {
                        if (palabra.length() > 2) //Consonante + Consonante + Vocal
                        {
                            otro = iniciaConsonante(palabra.substring(cantidadConsonante));
                            otro = otro + cantidadConsonante;
                            cantidadTotal = otro + iniciaVocal(palabra.substring(otro));
                        }
                        else
                        {
                            cantidadTotal = 1;
                        }
                    }
                }
                else
                {
                    cantidadTotal = 1;
                }
            }
            else if (cantidadDigito > 0)
            {
                cantidadTotal = cantidadDigito;
            }
            else
            {
                cantidadTotal = 1;
            }

            //invertimos la palabra aux            
            String aux = palabra.substring(0, cantidadTotal);
            
            aux = invertirPalabra(aux);
            
            lista.add(0, aux);
            palabra = removerPedazo(palabra, 0, cantidadTotal);            
        }

        //Inserta datos a la lista en caso de que haya datos en cola
        if (otra.size() > 0)
        {
            int tam = otra.size();

            for (int i = 0; i < tam; i++)
            {
                lista.add(0, otra.get(0));
                otra.remove(0);
            }
        }
        
        //Revisa que no haya silabas con consonantes vacias
        for (int i = 0; i < lista.size(); i++)
        {
        	String elemento = lista.get(i);
        	
        	if ( (elemento.length() == 1 && iniciaConsonanteSimple(elemento)) || 
        		 (elemento.length() == 2 && iniciaConsonanteEspecial(invertirPalabra(elemento))) )
        	{
        		//Reunimos las primera y segunda silaba a unir
        		String antes = elemento;
        		String siguiente = lista.get(i+1);
        		String nuevo = antes + siguiente;
        		        		
        		//Removemos los dos elementos concatenados
        		lista.remove(i);
        		lista.remove(i);
        		        	
        		//Agregamos la nueva silaba
        		lista.add(i, nuevo);
        		
        		//Disminuimos una posicion
        		i--;
        	}
        }
                
        return lista;
    }

    /**
     * Remover una parte de una cadena
     * 
     * @author   Jonathan Sandoval   <jonathan_s_pisis@yahoo.com.mx>
     * @param    String              Una cadena que será contada en partes.
     * @param    int                 posición desde donde se eliminará la cadena.
     * @param    int                 posición hasta donde se eliminará la cadena.
     * @return   String              la cadena original sin el pedazo selecionado.
     */
    public static String removerPedazo(String cadena, int inicio, int fin)
    {
        String cad = "";
        
        for (int i = 0; i < inicio; i++)
        {
            cad = cad + cadena.substring(i, i+1);
        }
        
        for (int i = fin; i < cadena.length(); i++)
        {
            cad = cad + cadena.substring(i, i+1);
        }
        
        return cad;
    }
    
    /**
     * Invierte el orden de una palabra como 'hola' a 'aloh'
     * @author   Jonathan Sandoval   <jonathan_s_pisis@yahoo.com.mx>
     * @param    String              Una palabra del idioma español.
     * @return   String              la cadena en orden inverso.
     */
    public static String invertirPalabra(String palabra)
    {
        String una = palabra;
        String dos = "";
        
        for (int i = palabra.length(); i > 0; i--)
        {            
            dos = una.substring(una.length() - i, una.length() - i + 1) + dos;
        }
        
        return dos;
    }
    
    /**
     * Devuelve si cierta palabra inicia con una vocal y que tipo de vocal es.
     * 
     * @author   Jonathan Sandoval <jonathan_s_pisis@yahoo.com.mx>
     * @param    String            Una palabra del idioma español.
     * @return   int               1 si es Hiato o vocal simple, 2 si es diptongo, 
       							   3 si es triptongo, 0 si no inicia con vocal.
     */
    public static int iniciaVocal(String palabra)
    {
    	int repeticiones = iniciaVocalRepetidaN(palabra);
    	    	
    	if (repeticiones >= 2)
    	{
    		return repeticiones;
    	}
    	else
    	{
	        if (iniciaTriptongo(palabra))
	        {
	            return 3;
	        }
	        else if (iniciaHiato(palabra))
	        {
	            return 1;
	        }
	        else if (iniciaDiptongo(palabra))
	        {
	            return 2;
	        }
	        else if (iniciaVocalFuerte(palabra) || iniciaVocalDebil(palabra))
	        {
	            return 1;
	        }
	        else
	        {
	            return 0;
	        }    		
    	}
    }

    /**
     * Devuelve la cantidad de digitos desde el inicio hasta una letra o al final
     * 
     * @author   Jonathan Sandoval   <jonathan_s_pisis@yahoo.com.mx>
     * @param    String              Una cadena del idioma español.
     * @return   int                 la cantidad de digitos que hay desde el inicio hasta una letra.
     */
    public static int esDigito(String palabra)
    {
        int cantidadDigito = 0;

        for (int i = 0; i < palabra.length(); i++)
        {
            if ( ("0".equals(palabra.substring(i, i+1))) || ("1".equals(palabra.substring(i, i+1))) ||
                 ("2".equals(palabra.substring(i, i+1))) || ("3".equals(palabra.substring(i, i+1))) ||
                 ("4".equals(palabra.substring(i, i+1))) || ("5".equals(palabra.substring(i, i+1))) ||
                 ("6".equals(palabra.substring(i, i+1))) || ("7".equals(palabra.substring(i, i+1))) ||
                 ("8".equals(palabra.substring(i, i+1))) || ("9".equals(palabra.substring(i, i+1))) )
            {
                cantidadDigito++;
            }
            else
            {
                return cantidadDigito;
            }
        }

        return cantidadDigito;
    }

    /**
     * Devuelve si inicia con un caracter de espacio
     * 
     * @author   Jonathan Sandoval   <jonathan_s_pisis@yahoo.com.mx>
     * @param    String              Una palabra del idioma español.
     * @return   boolean             Verdadero si inicia con ' ' o '\t' o '\n', sino falso.
     */
    public static boolean iniciaEspacio(String palabra)
    {
        if (" ".equals(palabra.substring(0,1)) 
        	|| "\t".equals(palabra.substring(0,1)) 
        	|| "\n".equals(palabra.substring(0,1)))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Devuelve si inicia con una consonante o si inicia con una consonante compuesta
     * 
     * @author   Jonathan Sandoval   <jonathan_s_pisis@yahoo.com.mx>
     * @param    String              Una palabra del idioma español
     * @return   int                 1 si es consonante simple, 0 si no es consonante,
                                     2 si inicia con dos consonante.
     */
    public static int iniciaConsonante(String palabra)
    {
    	if (palabra.equals(""))
    	{
    		return 0;
    	}
    	else
    	{    		
	        if (iniciaConsonanteEspecial(palabra))
            {
                return 2;
            }
	        else
	        {   
	        	int repeticiones = iniciaConsonanteRepetidaN(palabra);
	        	
	        	if (repeticiones >= 2)
	        	{
	        		return repeticiones;
	        	}
	        	else if (iniciaConsonanteSimple(palabra))
	        	{
	        		return 1;
	        	}
	        	else
	        	{
	        		return 0;
	        	}
	        }
    	}
    }

    /**
     * Devuelve true si inicia con alguna consonante de tamaño 1
     * 
     * @author   Jonathan Sandoval   <jonathan_s_pisis@yahoo.com.mx>
     * @param    String              Una palabra del idioma español.
     * @return 
     * @return   boolean             Verdadero si inicia con la letra L o la letra R.
     */
    public static boolean iniciaConsonanteSimple(String palabra)
    {
    	if (iniciaRL(palabra))
    	{
    		return true;
    	}
    	else if (iniciaConsonanteSobrante(palabra))
    	{
    		return true;
    	}
    	else if ("h".equals(palabra.substring(0,1)) || "j".equals(palabra.substring(0,1)) ||
    			 "m".equals(palabra.substring(0,1)) || "n".equals(palabra.substring(0,1)) ||
    			 "ñ".equals(palabra.substring(0,1)) || "q".equals(palabra.substring(0,1)) ||
				 "s".equals(palabra.substring(0,1)) || "v".equals(palabra.substring(0,1)) ||
				 "w".equals(palabra.substring(0,1)) || "x".equals(palabra.substring(0,1)) ||
				 "y".equals(palabra.substring(0,1)) || "z".equals(palabra.substring(0,1)) )
    	{
    		return true;
    	}
    	
    	return false;
    }
    
    /**
     * Devuelve si inicia con dos R o L
     * 
     * @author   Jonathan Sandoval   <jonathan_s_pisis@yahoo.com.mx>
     * @param    String              Una palabra del idioma español.
     * @return   boolean             Verdadero si inicia con la letra L o la letra R.
     */
    public static boolean iniciaRL(String palabra)
    {
        if ("r".equals(palabra.substring(0,1)) || "l".equals(palabra.substring(0,1)) )
        {
        	return true;
        }
        else
        {
        	return false;
        }
    }
    
    /**
     * Devuelve si inicia con una consonante como b , c , d , f , g , k , p , t
     * 
     * @author   Jonathan Sandoval   <jonathan_s_pisis@yahoo.com.mx>
     * @param    String              Una palabra del idioma español.
     * @return   boolean             Verdadero si inicia con la letra una letra citada arriba
     */
    public static boolean iniciaConsonanteSobrante(String palabra)
    {
        if ("b".equals(palabra.substring(0,1))  || "c".equals(palabra.substring(0,1))  || 
            "d".equals(palabra.substring(0,1))  || "f".equals(palabra.substring(0,1))  ||
            "g".equals(palabra.substring(0,1))  || "k".equals(palabra.substring(0,1))  ||
            "p".equals(palabra.substring(0,1))  || "t".equals(palabra.substring(0,1)))
        {
            return true;
        }
        
        return false;
    }
        
    /**
     * Devuelve si inicia con dos consonantes validas
     * 
     * @author   Jonathan Sandoval   <jonathan_s_pisis@yahoo.com.mx>
     * @param    String              Una palabra del idioma español.
     * @return   boolean             Verdadero si inicia con una consonante simple o con dos 
     								 consonantes valida tales como 'br' o 'bl' por poner ejemplo.
     */
    public static boolean iniciaConsonanteEspecial(String palabra)
    {
        if (palabra.length() > 1)
        {
        	//inicia con final de consonantes especiales
            if (iniciaRL(palabra.substring(0,1)))
            {       	
                if (iniciaConsonanteSobrante(palabra.substring(1,2)))
                {
                    return true;
                }
            }
            else if ("rr".equals(palabra.substring(0,2)))
            {
            	return true;
            }
            else if ("ll".equals(palabra.substring(0,2)))
            {
            	return true;
            }
            else if ("sb".equals(palabra.substring(0,2)))
            {
            	return true;
            }
            else if ("sd".equals(palabra.substring(0,2)))
            {
            	return true;
            }
            else if ("ht".equals(palabra.substring(0,2)))
            {
            	return true;
            }
            else if ("hc".equals(palabra.substring(0,2)))
            {
            	return true;
            }
        }
        
        return false;
    }

    /**
     * Devuelve si inicia con un Hiato
     * 
     * @author   Jonathan Sandoval   <jonathan_s_pisis@yahoo.com.mx>
     * @param    String              Una palabra del idioma español.
     * @return   boolean             Verdadero si inicia una combinación de dos vocales fuertes.
     */
    public static boolean iniciaHiato(String palabra)
    {
        if (palabra.length() <= 1)
        {       
            return false;
        }
        else
        {        	
            if ( (iniciaVocalFuerte(palabra.substring(0,1)) && iniciaVocalFuerte(palabra.substring(1,2)) ) ||
                 (iniciaVocalFuerte(palabra.substring(0,1)) && iniciaVocalDebilAcentuada(palabra.substring(1,2))) ||
                 (iniciaVocalDebilAcentuada(palabra.substring(0,1)) && iniciaVocalFuerte(palabra.substring(1,2))) )
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    /**
     * Devuelve si inicia con un Diptongo
     * 
     * @author   Jonathan Sandoval   <jonathan_s_pisis@yahoo.com.mx>
     * @param    String              Una palabra del idioma español.
     * @return   boolean             Verdadero si inicia con una combinación de 
     								 no más de una vocal fuerte.
     */
    public static boolean iniciaDiptongo(String palabra)
    {
        if (palabra.length() <= 1)
        {
            return false;
        }
        else
        {
            if ( (iniciaVocalDebil(palabra.substring(0,1)) && iniciaVocalDebil(palabra.substring(1,2)) ) ||
                 (iniciaVocalDebil(palabra.substring(0,1)) && iniciaVocalFuerte(palabra.substring(1,2)) ) ||
                 (iniciaVocalFuerte(palabra.substring(0,1)) && iniciaVocalDebil(palabra.substring(1,2)) ) )
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    /**
     * Devuelve si inicia con un Triptongo
     * 
     * @author   Jonathan Sandoval   <jonathan_s_pisis@yahoo.com.mx>
     * @param    String              Una palabra del idioma español.
     * @return   boolean             Verdadero si inicia con una combinación de no más de 
     								 dos vocales debiles y una fuerte.
     */
    public static boolean iniciaTriptongo(String palabra)
    {
        if (palabra.length() <= 2)
        {
            return false;
        }
        else
        {
            if ( (iniciaVocalDebil(palabra.substring(0,1)) && iniciaVocalDebil(palabra.substring(1,2)) && iniciaVocalFuerte(palabra.substring(2,3))) ||
                 (iniciaVocalDebil(palabra.substring(0,1)) && iniciaVocalFuerte(palabra.substring(1,2)) && iniciaVocalDebil(palabra.substring(2,3))) ||
                 (iniciaVocalFuerte(palabra.substring(0,1)) && iniciaVocalDebil(palabra.substring(1,2)) && iniciaVocalDebil(palabra.substring(2,3))) ||
                 (iniciaVocalDebil(palabra.substring(0,1)) && iniciaVocalDebil(palabra.substring(1,2)) && iniciaVocalDebil(palabra.substring(2,3))) )
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    /**
     * Devuelve si inicia con una vocal debil
     * 
     * @author   Jonathan Sandoval   <jonathan_s_pisis@yahoo.com.mx>
     * @param    String              Una palabra del idioma español.
     * @return   boolean             Verdadero si inicia con 'i' o 'u' o sus variantes.
     */
    public static boolean iniciaVocalDebil(String palabra)
    {
    	if (!(palabra.equals("") || palabra.equals(" ")))
    	{
	        if (("i".equals(palabra.substring(0,1))) || ("u".equals(palabra.substring(0,1))) ||
	            ("í".equals(palabra.substring(0,1))) || ("ú".equals(palabra.substring(0,1))) ||
	            ("ü".equals(palabra.substring(0,1))) )
	        {
	            return true;
	        }
    	}
    	
    	return false;
    }
    
    /**
     * Devuelve si inicia con una vocal fuerte
     * 
     * @author   Jonathan Sandoval   <jonathan_s_pisis@yahoo.com.mx>
     * @param    String              Una palabra del idioma español.
     * @return   boolean             Verdadero si inicia con 'a' o 'e' u 'o' o sus variantes.
     */
    public static boolean iniciaVocalFuerte(String palabra)
    {
    	if (!(palabra.equals("") || palabra.equals(" ")))
    	{
	    	if ( ("a".equals(palabra.substring(0,1))) || ("e".equals(palabra.substring(0,1))) ||
	             ("o".equals(palabra.substring(0,1))) || ("á".equals(palabra.substring(0,1))) ||
	             ("é".equals(palabra.substring(0,1))) || ("ó".equals(palabra.substring(0,1))) )
	        {
	            return true;
	        }
    	}
    	
    	return false;
    }
    
    /**
     * Devuelve si inicia con una vocal fuerte acentuada
     * 
     * @author   Jonathan Sandoval   <jonathan_s_pisis@yahoo.com.mx>
     * @param    String              Una palabra del idioma español.
     * @return   boolean             Verdadero si inicia con 'á' o 'é' u 'ó' o sus mayusculas.
     */
    public static boolean iniciaVocalFuerteAcentuada(String palabra)
    {
    	if (!(palabra.equals("") || palabra.equals(" ")))
    	{
	        if ( ("á".equals(palabra.substring(0,1))) || ("é".equals(palabra.substring(0,1))) || 
	             ("ó".equals(palabra.substring(0,1))) )
	        {
	            return true;
	        }
    	}
    	
    	return false;
    }
        
    /**
     * Devuelve si inicia con una vocal debil acentuada
     * 
     * @author   Jonathan Sandoval   <jonathan_s_pisis@yahoo.com.mx>
     * @param    String              Una palabra del idioma español.
     * @return   boolean             Verdadero si inicia con 'í' o 'ú' o sus mayusculas.
     */
    public static boolean iniciaVocalDebilAcentuada(String palabra)
    {
    	if (!(palabra.equals("") || palabra.equals(" ")))
    	{
	        if ( ("í".equals(palabra.substring(0,1))) || ("ú".equals(palabra.substring(0,1))) ) 
	        {
	            return true;
	        }
    	}
    	
    	return false;
    }    
    
    /**
     * Devuelve el numero de ocurrencias de una consonante repetida
     * 
     * @author   Jonathan Sandoval   <jonathan_s_pisis@yahoo.com.mx>
     * @param    String              Una palabra del idioma español.
     * @return   boolean             Verdadero si inicia con consonantes repetidas
     */
    public static int iniciaConsonanteRepetidaN(String palabra)
    {
    	//Revisamos que no sea una palabra vacia
    	if (palabra.equals(""))
    	{
    		return 0;
    	}

    	//Inicializacion de variables
    	int consonatesRepetidas = 0;
    	String aux = palabra;
    	char ultimoCaracter = aux.charAt(0);
    	char letraInicial = aux.charAt(0);
    	
    	while (aux.length() > 0 && iniciaConsonanteSimple(aux))
    	{
    		//revisamos que el ultimo caracter coincida con el primero de nuestra cadena
    		if (ultimoCaracter != letraInicial)
    		{
    			break;
    		}
    		
    		//Aumentamos y partimos la cadena
    		consonatesRepetidas++;
    		aux = aux.substring(1); 
    		ultimoCaracter = !aux.equals("") ? aux.charAt(0) : '\0';    
       	}    	
    	
    	if (consonatesRepetidas == palabra.length())
    	{
    		return consonatesRepetidas;
    	}
    	else
    	{
        	return consonatesRepetidas - 1;
    	}
    }
    
    /**
     * Devuelve el numero de ocurrencias de una vocal repetida
     * 
     * @author   Jonathan Sandoval   <jonathan_s_pisis@yahoo.com.mx>
     * @param    String              Una palabra del idioma español.
     * @return   boolean             Verdadero si inicia con vocales repetidas
     */
    public static int iniciaVocalRepetidaN(String palabra)
    {
    	//Revisamos que no sea una palabra vacia
    	if (palabra.equals(""))
    	{
    		return 0;
    	}
    	
    	//Inicializacion de variables
    	int vocalesRepetidas = 0;
    	String aux = palabra;
    	char ultimoCaracter = aux.charAt(0);
    	char letraInicial = aux.charAt(0);
    	
		//revisamos que el ultimo caracter coincida con el primero de nuestra cadena
    	while (aux.length() > 0 && (iniciaVocalDebil(aux) || iniciaVocalFuerte(aux)) )
    	{
    		if (ultimoCaracter != letraInicial)
    		{
    			break;
    		}

    		//Aumentamos y partimos la cadena
    		vocalesRepetidas++;
    		aux = aux.substring(1);    		    		
    		ultimoCaracter = !aux.equals("") ? aux.charAt(0) : '\0';
    	}
    	
    	return vocalesRepetidas;
    }
    
    /***************           Funciones de tipo de Palabra   ************************************/
    
    /**
     * Devuelve el tipo de palabra que es foneticamente
     * @param  LinkedList<String>    una lista con las silabas de una palabra
     * @return String                El nombre del tipo de palabra como Aguda, Grave o Esdrújula
     */
    public static String tipoPalabra(LinkedList<String> silabas)
    {
    	String silabaActual   = "";
    	String charActual     = "";
    	String ultimaSilaba   = silabas.get(silabas.size() - 1);
    	char ultimoCaracter   = ultimaSilaba.charAt(ultimaSilaba.length() - 1);
    	
    	if (silabas.size() == 1)
    	{
    		String silaba = silabas.get(0); 
    		if (silaba.equals("") || iniciaEspacio(silaba))
			{
    			return "Espacio en Blanco";
			}
    		else
    		{
	    		return "Monosílaba";
    		}
    	}

    	for (int i = 0; i < silabas.size(); i++)
    	{
    		silabaActual = silabas.get(silabas.size() - i - 1);

    		for (int j = 0; j < silabaActual.length(); j++)
    		{
    			charActual = silabaActual.substring(j,j+1);
    			
    			if (iniciaVocalDebilAcentuada(charActual) || iniciaVocalFuerteAcentuada(charActual))
    			{
    				if (i == 0)
    				{
    					return "Aguda";
    				}
    				else if (i == 1)
    				{
    					return "Grave";
    				}
    				else if (i == 2)
    				{
    					return "Esdrújula";
    				}
    				else
    				{
    					return "Sobresdrújula";
    				}
    			}
    		}
    	}
    	
    	if (ultimoCaracter == 'n' || ultimoCaracter == 's' || ultimoCaracter == 'N' || 
    		ultimoCaracter == 'S' || iniciaVocal(Character.toString(ultimoCaracter)) > 0)
    	{
    		return "Grave";
    	}
    	else if (iniciaConsonante(Character.toString(ultimoCaracter)) > 0)
       	{
    		return "Aguda";
    	}
    	else
    	{
    		return "Palabra Invalida";
    	}
    }
}
