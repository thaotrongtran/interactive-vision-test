using System;
using System.IO;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Azure.WebJobs;
using Microsoft.Azure.WebJobs.Extensions.Http;
using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.Logging;
using Newtonsoft.Json;
using System.Data.SqlClient;


namespace answerLog
{
    public static class Function1
    {
        [FunctionName("Function1")]
        public static async Task<IActionResult> Run(
            [HttpTrigger(AuthorizationLevel.Function, "get", "post", Route = null)] HttpRequest req,
            ILogger log)
        {
            log.LogInformation("C# HTTP trigger function processed a request.");

            string character = req.Query["character"];

            string requestBody = await new StreamReader(req.Body).ReadToEndAsync();
            dynamic data = JsonConvert.DeserializeObject(requestBody);
            character = character ?? data?.character;

            //For SQL server
            // Get the connection string from app settings and use it to create a connection.
            var str = Environment.GetEnvironmentVariable("sqldb_connection");
            using (SqlConnection conn = new SqlConnection(str))
            {
                conn.Open();
                //Get the current time
                DateTime utcDate = DateTime.UtcNow;
                log.LogInformation(utcDate.ToString());
                string time = utcDate.ToString();

                string insertString = $"INSERT into Answers(Character) VALUES ('{character}');";
                log.LogInformation(insertString);
                SqlCommand cmd = new SqlCommand(insertString, conn);
                cmd.ExecuteNonQuery();

                var text = "SELECT * FROM Answers";
                cmd = new SqlCommand(text, conn);
                SqlDataReader rdr = cmd.ExecuteReader();
                while (rdr.Read())
                {
                    log.LogInformation(rdr[0].ToString());
                }
            }

            return character != null
                ? (ActionResult)new OkObjectResult($"Hello, {character}")
                : new BadRequestObjectResult("Please pass a name on the query string or in the request body");
        }
    }
}
