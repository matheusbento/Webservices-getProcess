/*
 * Copyright (c) 2010, Oracle. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * * Neither the name of Oracle nor the names of its contributors
 *   may be used to endorse or promote products derived from this software without
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.me.calculator;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.sql.*;

/**
 *
 * @author User
 */
@WebService()
public class ProgressWS {

    /**
     * Operação de Web service
     */
    @WebMethod(operationName = "getProgress")
    public String getProgress(@WebParam(name = "ra") String ra) {
        String quantidadeQuestao = "";
        String quantidadeResposta = "";
        String quantidade = "";
       try
    {
      // create our mysql database connection
      String myDriver = "org.gjt.mm.mysql.Driver";
      String myUrl = "jdbc:mysql://localhost/uenp";
      Class.forName(myDriver);
      Connection conn = DriverManager.getConnection(myUrl, "root", "");
      
      // our SQL SELECT query. 
      // if you only need a few columns, specify them by name instead of using "*"
      String query = "SELECT count(*) as quantidade FROM `questoes`";
      String query2 = "SELECT count(*) as quantidade FROM `respostas` where ativo=true and ra='"+ra+"'";

      // create the java statement
      Statement st = conn.createStatement();
      Statement st2 = conn.createStatement();
      
      // execute the query, and get a java resultset
      ResultSet rs = st.executeQuery(query);
      ResultSet rs2 = st2.executeQuery(query2);
      
      // iterate through the java resultset,
       
      rs.next();
      
       quantidadeQuestao = rs.getString("quantidade");
       if(quantidadeQuestao.isEmpty()){
       quantidadeQuestao = "0";
       }
        
     rs2.next();
      
       quantidadeResposta = rs2.getString("quantidade");
        if(quantidadeResposta.isEmpty()){
       quantidadeResposta = "0";
       }
      
      int soma = (100*Integer.parseInt(quantidadeResposta))/Integer.parseInt(quantidadeQuestao);
      quantidade = Integer.toString(soma);
      st.close();
      st2.close();
    }
    catch (Exception e)
    {
      System.err.println("Got an exception! ");
      System.err.println(e.getMessage());
    }
        
        return quantidade;
    }
}
