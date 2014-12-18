using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using LumenWorks.Framework.IO.Csv;
using System.Web.UI.WebControls;
using System.IO;
using System.Web.UI.HtmlControls;

public partial class epl : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {


        HtmlTableRow row1 = new HtmlTableRow();

        HtmlTableCell headerCell0 = new HtmlTableCell();
        HtmlTableCell headerCell1 = new HtmlTableCell();
        HtmlTableCell headerCell2 = new HtmlTableCell();

        HtmlTableCell headerCell3 = new HtmlTableCell();
        HtmlTableCell headerCell4 = new HtmlTableCell();
        HtmlTableCell headerCell5 = new HtmlTableCell();
        using (CsvReader csv = new CsvReader(
           new StreamReader("F:\\workspace\\D3_Visualization\\EPL\\App_Data\\epl_tweet.csv"), true))
        {
            // missing fields will not throw an exception,
            // but will instead be treated as if there was a null value
            csv.MissingFieldAction = MissingFieldAction.ReplaceByNull;
            // to replace by "" instead, then use the following action:
            //csv.MissingFieldAction = MissingFieldAction.ReplaceByEmpty;
            int fieldCount = csv.FieldCount;
            string[] headers = csv.GetFieldHeaders();
            String d = csv.GetCurrentRawData();
            int i = 0;
            headerCell0.InnerHtml = headers[i++];
            i++;
            headerCell1.InnerHtml = "Language";
            headerCell2.InnerHtml = headers[i++];
            headerCell3.InnerHtml = "Profile Image";
            headerCell4.InnerHtml = "Tweet Description";


            row1.Cells.Add(headerCell0);
            row1.Cells.Add(headerCell1);
            row1.Cells.Add(headerCell2);
            row1.Cells.Add(headerCell3);
            row1.Cells.Add(headerCell4);


            row1.BorderColor = "blue";
            tb1.Rows.Add(row1);




            i = 0;
            while (csv.ReadNextRecord())
            {

                HtmlTableRow row2 = new HtmlTableRow();

                HtmlTableCell dataCell = new HtmlTableCell();
                HtmlTableCell dataCell1 = new HtmlTableCell();
                HtmlTableCell dataCell2 = new HtmlTableCell();
                HtmlTableCell dataCell3 = new HtmlTableCell();
                HtmlTableCell dataCell4 = new HtmlTableCell();
                dataCell.InnerHtml = csv[i++];
                dataCell1.InnerHtml = csv[i++];
                dataCell2.InnerHtml = csv[i++];
                dataCell3.InnerHtml = "<img src=" + csv[i++] + ">";
                dataCell4.InnerHtml = csv[i++];


                row2.Cells.Add(dataCell);
                row2.Cells.Add(dataCell1);
                row2.Cells.Add(dataCell2);
                row2.Cells.Add(dataCell3);
                row2.Cells.Add(dataCell4);

                tb1.Rows.Add(row2);

                i = 0;
            }

        }


    }

    
    protected void show(object sender, EventArgs e)
    {


    }
}