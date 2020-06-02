package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

//@Controller
//@SpringBootApplication
@RestController
@EnableAutoConfiguration
public class DemoApplication {

	   public static String findLongestPalindrome(String givenStr) {
		      return findLongestPalindrome(givenStr, 0);
		   }


		   // Given a string of length at most 1000, find its longest palindromic substring.
		   public static String findLongestPalindrome(String givenStr, int debugFlag) {
		      if (givenStr == null) {
		         return null;
		      }

		      int givenStrLen = givenStr.length();
		      if (givenStrLen < 1 || givenStrLen > 1000) {
		         return "";
		      }

		      int maxPalLenFound = -1;
		      String longestPalFound = null;

		      int givenStrLenHalf   = givenStrLen / 2;
		      int givenStrParity    = givenStrLen % 2;
		      //int givenStrCenterAdj = givenStrLenHalf;         // Center based on zero-based indexing.
		      //int givenStrCenter    = givenStrCenterAdj + 1;   // Center based on one-based  indexing.

		      int cenOffsetSign = -999;
		      int currCen       = -1;
		      int currCenLeft   = -1;
		      int leftmostIdx   = -1;
		      int rightmostIdx  = -1;

		      char[] givenStrChars = givenStr.toCharArray();

		      /* These two outermost loops will iterate each character of the given string from the center working alternately left
		         and right.  For example, if the length of the given string is 41, the currCen variable will take on the values
		         20, 19, 21, 18, 22, ..., 10, 30, ..., 3, 37, 2, 38, 1, 39, 0, 40.  If the length is 40, currCen will take on the
		         same sequence of values except for the 40.
		      */

		      CENOFFLOOP:
		      for (int iCenOffset = 0; iCenOffset <= givenStrLenHalf; iCenOffset++) {
		         for (int iCenOffsetSide = 0; iCenOffsetSide <= 1; iCenOffsetSide++) {
		            cenOffsetSign = 2 * iCenOffsetSide - 1;    // 0 --> -1 for left and 1 --> 1 for right
		            if ((iCenOffset == 0 && cenOffsetSign == 1) ||
		                (iCenOffset == givenStrLenHalf && givenStrParity == 0 && cenOffsetSign == 1)) {
		               if (debugFlag > 0) {
		                  System.out.println("Continuing for iCenOffset = " + iCenOffset + " and cenOffsetSign = " + cenOffsetSign);
		               }
		               continue;
		            }

		            currCen     = givenStrLenHalf + cenOffsetSign * iCenOffset;
		            currCenLeft = givenStrLenHalf - iCenOffset;
		            if (debugFlag > 0) {
		               System.out.println("currCen = " + currCen + " for iCenOffset = " + iCenOffset + " and cenOffsetSign = " +
		                  cenOffsetSign);
		            }

		            if (maxPalLenFound > 2 * currCen + 1) {
		               // No remaining substrings to be checked could represent a longer palindrome than what has already been found.
		               // So, we can break out of the outer loop.
		               break CENOFFLOOP;
		            }

		            if (iCenOffset == 0) {
		               maxPalLenFound  = 1;
		               longestPalFound = String.valueOf(givenStrChars[currCen]);
		               if (debugFlag > 0) {
		                  System.out.println("   Updated maxPalLenFound to " + maxPalLenFound + " and longestPalFound to \"" +
		                     longestPalFound + "\"");
		               }
		            }


		            // Check for odd-length palindrome centered on currCen.
		            for (int iCurrCenOffset = 1; iCurrCenOffset <= currCenLeft; iCurrCenOffset++) {
		               leftmostIdx  = currCen - iCurrCenOffset;
		               rightmostIdx = currCen + iCurrCenOffset;
		               if (debugFlag > 0) {
		                  System.out.println("   iCurrCenOffset = " + iCurrCenOffset + ", leftmostIdx = " + leftmostIdx +
		                     ", rightmostIdx = " + rightmostIdx);
		               }
		               if (leftmostIdx < 0 || rightmostIdx >= givenStrLen ||
		                   givenStrChars[leftmostIdx] != givenStrChars[rightmostIdx]) {
		                  break;
		               }
		               if (2 * iCurrCenOffset + 1 > maxPalLenFound) {
		                  maxPalLenFound = 2 * iCurrCenOffset + 1;
		                  longestPalFound = givenStr.substring(leftmostIdx, leftmostIdx + maxPalLenFound);
		                  if (debugFlag > 0) {
		                     System.out.println("   Updated maxPalLenFound to " + maxPalLenFound + " and longestPalFound to \"" +
		                        longestPalFound + "\"");
		                  }
		               }
		            }  // end of check for odd-length palindrome centered on currCen


		            // Check for even-length palindrome centered on currCen and the character to left.
		            if (currCen > 0 && givenStrChars[currCen-1] == givenStrChars[currCen]) {
		               if (maxPalLenFound < 2) {
		                  maxPalLenFound  = 2;
		                  longestPalFound = givenStr.substring(currCen-1, currCen+1);
		                  if (debugFlag > 0) {
		                     System.out.println("   Updated maxPalLenFound to " + maxPalLenFound + " and longestPalFound to \"" +
		                        longestPalFound + "\"");
		                  }
		               }

		               for (int iCurrCenOffset = 1; iCurrCenOffset <= currCenLeft; iCurrCenOffset++) {
		                  leftmostIdx  = currCen - 1 - iCurrCenOffset;
		                  rightmostIdx = currCen + iCurrCenOffset;
		                  if (debugFlag > 0) {
		                     System.out.println("   iCurrCenOffset = " + iCurrCenOffset + ", leftmostIdx = " + leftmostIdx +
		                        ", rightmostIdx = " + rightmostIdx);
		                  }
		                  if (leftmostIdx < 0 || rightmostIdx >= givenStrLen ||
		                      givenStrChars[leftmostIdx] != givenStrChars[rightmostIdx]) {
		                     break;
		                  }

		                  if (2 * (iCurrCenOffset + 1) > maxPalLenFound) {
		                     maxPalLenFound = 2 * (iCurrCenOffset + 1);
		                     longestPalFound = givenStr.substring(leftmostIdx, leftmostIdx + maxPalLenFound);
		                     if (debugFlag > 0) {
		                        System.out.println("   Updated maxPalLenFound to " + maxPalLenFound + " and longestPalFound to \"" +
		                           longestPalFound + "\"");
		                     }
		                  }
		               }
		            }  // end of check for even-length palindrome centered on currCen and the character to left
		         }  // end of iCenOffsetSide "for" loop
		      }  // end of iCenOffset "for" loop


		      return longestPalFound;
		   }  // end of method findLongestPalindrome(String, int)


		   public static void executeLongestPalindromeTests() {
		      executeLongestPalindromeTests(0);
		   }


		   public static void executeLongestPalindromeTests(int debugFlag) {
		      String fillerStr10 = "0123456789";

		      String fillerStr100 = "";
		      for (int iFill = 1; iFill <= 10; iFill++) {
		         fillerStr100 = fillerStr100 + fillerStr10;
		      }

		      String fillerStr1000 = "";
		      for (int iFill = 1; iFill <= 10; iFill++) {
		         fillerStr1000 = fillerStr1000 + fillerStr100;
		      }

		      List<String> testStrings = Arrays.asList(
		         null,
		         "",
		         "a",

		         "aa",
		         "ab",

		         "aaa",
		         "aba",
		         "abc",

		         "aaaa",
		         "abaa",
		         "aaba",
		         "abba",

		         "abcdeedcbzzzzz",
		         "abcdeedcbzzzzzz",
		         "abcdeedcbzzzzzyyyy",
		         "abcdeedcbzzzzzyyyyy",

		         "abcdxedcbzzzzz",
		         "abcdxedcbzzzzzz",
		         "abcdxedcbzzzzzyyyy",
		         "abcdxedcbzzzzzyyyyy",

		         "abcdeedcbzzzzzyyyyy" + fillerStr10 + fillerStr10,
		         fillerStr10 + "abcdeedcbzzzzzyyyyy" + fillerStr10,
		         fillerStr10 + fillerStr10 + "abcdeedcbzzzzzyyyyy",

		         "abcdeedcbzzzzzyyyyy" + fillerStr100 + fillerStr100,
		         fillerStr100 + "abcdeedcbzzzzzyyyyy" + fillerStr100,
		         fillerStr100 + fillerStr100 + "abcdeedcbzzzzzyyyyy",

		         "abcdeedcbzzzzzyyyyy" + fillerStr100 + fillerStr100 + fillerStr100 + fillerStr100 + fillerStr100 + fillerStr100 + fillerStr100 + fillerStr100 + fillerStr100,
		         fillerStr100 + fillerStr100 + fillerStr100 + fillerStr100 + "abcdeedcbzzzzzyyyyy" + fillerStr100 + fillerStr100 + fillerStr100 + fillerStr100 + fillerStr100,
		         fillerStr100 + fillerStr100 + fillerStr100 + fillerStr100 + fillerStr100 + fillerStr100 + fillerStr100 + fillerStr100 + fillerStr100 + "abcdeedcbzzzzzyyyyy",

		         "abcdxedcbzzzzzyyyyy" + fillerStr100 + fillerStr100 + fillerStr100 + fillerStr100 + fillerStr100 + fillerStr100 + fillerStr100 + fillerStr100 + fillerStr100,
		         fillerStr100 + fillerStr100 + fillerStr100 + fillerStr100 + "abcdxedcbzzzzzyyyyy" + fillerStr100 + fillerStr100 + fillerStr100 + fillerStr100 + fillerStr100,
		         fillerStr100 + fillerStr100 + fillerStr100 + fillerStr100 + fillerStr100 + fillerStr100 + fillerStr100 + fillerStr100 + fillerStr100 + "abcdxedcbzzzzzyyyyy",

		         fillerStr1000,
		         fillerStr1000 + "x");

		      String longestPal = null;
		      for (String testStr: testStrings) {
		         System.out.println("Test string:         \"" + testStr + "\"");
		         longestPal = findLongestPalindrome(testStr, debugFlag);
		         System.out.println("Longest palindrome:  \"" + longestPal + "\"\n");
		      }
		   } // end of method executeLongestPalindromeTests(int)

	
	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "Hello from example Spring Boot application!";
	}
	
	@GetMapping("/finlonpal")
	public String finLonPal(@RequestParam(name="givenstr",  required = true,  defaultValue = "")  String givenStr,
			                @RequestParam(name="debugflag", required = false, defaultValue = "0") String debugFlagStr) {
		int debugFlag = (debugFlagStr != null && (debugFlagStr.equalsIgnoreCase("0") ||
							debugFlagStr.equalsIgnoreCase("1")) ? Integer.parseInt(debugFlagStr) : 0);
		String longestPal = "";
		
         if (givenStr.equalsIgnoreCase("runtests")) {
            executeLongestPalindromeTests(debugFlag);
         } else {
            System.out.println("Given string:        \"" + givenStr + "\"");
            // longestPal = findLongestPalindrome(givenStr);
            longestPal = findLongestPalindrome(givenStr, debugFlag);
            System.out.println("Longest palindrome:  \"" + longestPal + "\"");
         }
         
		return "Given string:  \"" + givenStr + "\" and debugFlagStr = \"" + debugFlagStr +
			"\" and longestPal = \"" + longestPal + "\"";
	}

	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
