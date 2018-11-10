using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using ClueGoTesting.Models;

namespace ClueGoTesting.Helper
{
    public class HelperClass
    {
        public Case CreateCase(string name,int caseNumber)
        {
            var createdCase = new Case()
            {
                GameInfo = "this is "+name+ "with number " + caseNumber + ".someone was killed",
                
            };
            
            return createdCase;
        }

        

    }
}
