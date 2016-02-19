/* CS144
 *
 * Parser skeleton for processing item-???.xml files. Must be compiled in
 * JDK 1.5 or above.
 *
 * Instructions:
 *
 * This program processes all files passed on the command line (to parse
 * an entire diectory, type "java MyParser myFiles/*.xml" at the shell).
 *
 * At the point noted below, an individual XML file has been parsed into a
 * DOM Document node. You should fill in code to process the node. Java's
 * interface for the Document Object Model (DOM) is in package
 * org.w3c.dom. The documentation is available online at
 *
 * http://java.sun.com/j2se/1.5.0/docs/api/index.html
 *
 * A tutorial of Java's XML Parsing can be found at:
 *
 * http://java.sun.com/webservices/jaxp/
 *
 * Some auxiliary methods have been written for you. You may find them
 * useful.
 */

package edu.ucla.cs.cs144;

import java.io.*;
import java.text.*;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ErrorHandler;


class MyParser {
    
    static final String columnSeparator = "|*|";
    static DocumentBuilder builder;
    
    static final String[] typeName = {
    "none",
    "Element",
    "Attr",
    "Text",
    "CDATA",
    "EntityRef",
    "Entity",
    "ProcInstr",
    "Comment",
    "Document",
    "DocType",
    "DocFragment",
    "Notation",
    };
    
    static class MyErrorHandler implements ErrorHandler {
        
        public void warning(SAXParseException exception)
        throws SAXException {
            fatalError(exception);
        }
        
        public void error(SAXParseException exception)
        throws SAXException {
            fatalError(exception);
        }
        
        public void fatalError(SAXParseException exception)
        throws SAXException {
            exception.printStackTrace();
            System.out.println("There should be no errors " +
                               "in the supplied XML files.");
            System.exit(3);
        }
        
    }
    
    /* Non-recursive (NR) version of Node.getElementsByTagName(...)
     */
    static Element[] getElementsByTagNameNR(Element e, String tagName) {
        Vector< Element > elements = new Vector< Element >();
        Node child = e.getFirstChild();
        while (child != null) {
            if (child instanceof Element && child.getNodeName().equals(tagName))
            {
                elements.add( (Element)child );
            }
            child = child.getNextSibling();
        }
        Element[] result = new Element[elements.size()];
        elements.copyInto(result);
        return result;
    }
    
    /* Returns the first subelement of e matching the given tagName, or
     * null if one does not exist. NR means Non-Recursive.
     */
    static Element getElementByTagNameNR(Element e, String tagName) {
        Node child = e.getFirstChild();
        while (child != null) {
            if (child instanceof Element && child.getNodeName().equals(tagName))
                return (Element) child;
            child = child.getNextSibling();
        }
        return null;
    }
    
    /* Returns the text associated with the given element (which must have
     * type #PCDATA) as child, or "" if it contains no text.
     */
    static String getElementText(Element e) {
        if (e.getChildNodes().getLength() == 1) {
            Text elementText = (Text) e.getFirstChild();
            return elementText.getNodeValue();
        }
        else
            return "";
    }
    
    /* Returns the text (#PCDATA) associated with the first subelement X
     * of e with the given tagName. If no such X exists or X contains no
     * text, "" is returned. NR means Non-Recursive.
     */
    static String getElementTextByTagNameNR(Element e, String tagName) {
        Element elem = getElementByTagNameNR(e, tagName);
        if (elem != null)
            return getElementText(elem);
        else
            return "";
    }
    
    /* Returns the amount (in XXXXX.xx format) denoted by a money-string
     * like $3,453.23. Returns the input if the input is an empty string.
     */
    static String strip(String money) {
        if (money.equals(""))
            return money;
        else {
            double am = 0.0;
            NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
            try { am = nf.parse(money).doubleValue(); }
            catch (ParseException e) {
                System.out.println("This method should work for all " +
                                   "money values you find in our data.");
                System.exit(20);
            }
            nf.setGroupingUsed(false);
            return nf.format(am).substring(1);
        }
    }
    
    /* Process one items-???.xml file.
     */
    static void processFile(File xmlFile) {
        Document doc = null;
        try {
            doc = builder.parse(xmlFile);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(3);
        }
        catch (SAXException e) {
            System.out.println("Parsing error on file " + xmlFile);
            System.out.println("  (not supposed to happen with supplied XML files)");
            e.printStackTrace();
            System.exit(3);
        }
        
        /* At this point 'doc' contains a DOM representation of an 'Items' XML
         * file. Use doc.getDocumentElement() to get the root Element. */
        System.out.println("Successfully parsed - " + xmlFile);
        
        /* Fill in code here (you will probably need to write auxiliary
            methods). */
    /**************************************************************/
        Element[] elems = getElementsByTagNameNR(doc.getDocumentElement(), "Item");
        for(Element e : elems) {
            //string represents attribute of one tuple in database
            String itemId = e.getAttribute("ItemID");
            String name = getElementTextByTagNameNR(e, "Name");
            String currently = strip(getElementTextByTagNameNR(e, "Currently"));
            String buy_price = strip(getElementTextByTagNameNR(e, "Buy_Price"));
            String first_bid = strip(getElementTextByTagNameNR(e, "First_Bid"));
            String number_of_bids = getElementTextByTagNameNR(e, "Number_of_Bids");
            String country = getElementTextByTagNameNR(e, "Country");
            String started = changetimeformat(getElementTextByTagNameNR(e, "Started"));
            String ends = changetimeformat(getElementTextByTagNameNR(e, "Ends"));
            Element seller = getElementByTagNameNR(e, "Seller");
            String sellerId = seller.getAttribute("UserID");
            String sellerRatings = seller.getAttribute("Rating");
            String location = getElementTextByTagNameNR(e, "Location");
            Element locate = getElementByTagNameNR(e, "Location");
            String latitude = locate.getAttribute("Latitude");
            String longitude = locate.getAttribute("Longitude");
            String description = getElementTextByTagNameNR(e, "Description");
            if(description.length() > 4000)
                description = description.substring(0, 4001);

            // add the record to the list
            items.add(itemRecord(itemId, name, currently, buy_price, first_bid, number_of_bids, country,
                started, ends, sellerId, location, latitude, longitude, description));

            // add the category infomation to category and belongto information to belong
            Element[] categories = getElementsByTagNameNR(e, "Category");
            for(Element c : categories) {
                String cate = getElementText(c);
                if(category.containsKey(cate) == false) 
                    category.put(cate, category.size());
                belong.add(itemId + columnSeparator + category.get(cate));
            }

            // add the user information
            if(user.containsKey(sellerId)) 
                user.get(sellerId).sellRating = sellerRatings;
            
            else {
                User u = new User(sellerId);
                u.sellRating = sellerRatings;
                user.put(sellerId, u);
            }

            // add the bid infromation and bider inforamtion
            Element[] ebids = getElementsByTagNameNR(getElementByTagNameNR(e, "Bids"), "Bid");
            for(Element b : ebids) {
                Element bidder = getElementByTagNameNR(b, "Bidder");
                String bidderId = bidder.getAttribute("UserID");
                String bidderRating = bidder.getAttribute("Rating");
                String bidderloc = getElementTextByTagNameNR(bidder, "Location");
                String biddercoun = getElementTextByTagNameNR(bidder, "Country");
                String amount = strip(getElementTextByTagNameNR(b, "Amount"));
                String time = changetimeformat(getElementTextByTagNameNR(b, "Time"));

                if(user.containsKey(bidderId) == false) {
                    User u = new User(bidderId);
                    user.put(bidderId, u);
                }
                //set the value for user
                user.get(bidderId).location = bidderloc;
                user.get(bidderId).country = biddercoun;
                user.get(bidderId).bidRating = bidderRating;
                bid.add(bidRecord(itemId, bidderId, time, amount));
            }

        }
        
        
        
        
    }
    //Use hash table to store instances, for users, when a instance created, we maybe do not  
    //know all the attributes value,so just map a string to a data structure User which may 
    // include whole or part of the information about the user
    //For bids, there will be no duplicate so we use just ArrayList
    
    private static HashSet<String> items;
    private static HashMap<String, Integer> category;
    private static HashMap<String, User> user;
    private static HashSet<String> belong;
    private static ArrayList<String> bid;
    private static SimpleDateFormat newformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat oldformat = new SimpleDateFormat("MMM-dd-yy HH:mm:ss");

    private static String changetimeformat(String date) {
        try {
            return newformat.format(oldformat.parse(date));
        }
        catch(Exception e) {
            System.out.println(e);
        }
        return "";
    }
    //one instance of itemRecord is a item in record
    private static String itemRecord(String itemID, String name, String currently, String buy_price,
        String first_bid, String number_of_bids, String country, String started, String ends,
        String sellerId, String location, String latitude, String longitude, String description) {

        StringBuffer record = new StringBuffer();
        //add the string for each attribute with separator
        record.append(itemID); record.append(columnSeparator); 
        record.append(name); record.append(columnSeparator);
        record.append(currently); record.append(columnSeparator);
        record.append(buy_price.equals("")?"\\N":buy_price);  record.append(columnSeparator);
        record.append(first_bid); record.append(columnSeparator);
        record.append(number_of_bids); record.append(columnSeparator);
        record.append(location); record.append(columnSeparator);
        record.append(latitude.equals("")?"\\N":latitude); record.append(columnSeparator);
        record.append(longitude.equals("")?"\\N":longitude);  record.append(columnSeparator);
        record.append(country); record.append(columnSeparator);
        record.append(started); record.append(columnSeparator);
        record.append(ends); record.append(columnSeparator);
        record.append(sellerId); record.append(columnSeparator);
        record.append(description);

        return record.toString();
    }
    //one instance of bidRecord is a bid in record
    private static String bidRecord(String itemId, String userId, String time, String amount) {
        StringBuffer record = new StringBuffer();
        //add the string for each attribute with separator
        record.append(itemId); record.append(columnSeparator);
        record.append(userId); record.append(columnSeparator);
        record.append(time); record.append(columnSeparator);
        record.append(amount);

        return record.toString();
    }
    //one instance of User is a user
    private static class User {
        String userID, location, country;
        String sellRating, bidRating;
        
        public User(String userid) {
            userID = userid;
            location = "";
            country = "";
            sellRating = "";
            bidRating = "";
        }

        public String getstring() {
            StringBuffer record = new StringBuffer();
            record.append(userID); record.append(columnSeparator);
            record.append(location.equals("")?"\\N":location);  record.append(columnSeparator);
            record.append(country.equals("")?"\\N":location);  record.append(columnSeparator);
            record.append(sellRating.equals("")?"\\N":sellRating); record.append(columnSeparator);
            record.append(bidRating.equals("")?"\\N":bidRating);
            return record.toString();
        }
    }
// the write method to write the data on documents
    public static void writetofile(){
        try {
            System.out.println("Start to write to file");
            //use .txt file to store the records because it is easy to check the contents on any
            // operating system
            BufferedWriter bwItem = new BufferedWriter(new FileWriter("item.txt"));
            BufferedWriter bwCategory = new BufferedWriter(new FileWriter("category.txt"));
            BufferedWriter bwBid = new BufferedWriter(new FileWriter("bid.txt"));
            BufferedWriter bwBelong = new BufferedWriter(new FileWriter("belong.txt"));
            BufferedWriter bwUser = new BufferedWriter(new FileWriter("user.txt"));
            

            // write user information to user.txt
            for(String str : user.keySet()) {
                bwUser.write(user.get(str).getstring());
                bwUser.newLine();
            }

            // write item information to item.txt
            for(String str : items) {
                bwItem.write(str);
                bwItem.newLine();
            }

            // write category information to catogory.tx
            for(String str : category.keySet()) {
                bwCategory.write(category.get(str) + columnSeparator + str);
                bwCategory.newLine();
            }

            // write the belong information to belong.txt
            for(String str : belong) {
                bwBelong.write(str);
                bwBelong.newLine();
            }

            // write the bid information to bid.txt
            for(String str : bid) {
                bwBid.write(str);
                bwBid.newLine();
            }

            bwItem.close();
            bwUser.close();
            bwCategory.close();
            bwBid.close();
            bwBelong.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void main (String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java MyParser [file] [file] ...");
            System.exit(1);
        }
        
        /* Initialize parser. */
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            factory.setIgnoringElementContentWhitespace(true);      
            builder = factory.newDocumentBuilder();
            builder.setErrorHandler(new MyErrorHandler());
        }
        catch (FactoryConfigurationError e) {
            System.out.println("unable to get a document builder factory");
            System.exit(2);
        } 
        catch (ParserConfigurationException e) {
            System.out.println("parser was unable to be configured");
            System.exit(2);
        }
        
        /* Process all files listed on command line. */
        items = new HashSet<String>();
        category = new HashMap<String, Integer>();
        user = new HashMap<String, User>();
        belong = new HashSet<String>();
        bid = new ArrayList<String>();
        for (int i = 0; i < args.length; i++) {
            File currentFile = new File(args[i]);
            processFile(currentFile);
        }
        writetofile();
    }
}