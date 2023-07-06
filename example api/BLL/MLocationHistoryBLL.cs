using NawaDataDAL.Common;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using NawaDataProjectDAL;

namespace NawaDataProjectBLL
{
    public class MLocationHistoryBLL
    {
        public void MlocationLog(PostLocation postLocation)
        {
            try
            {
                string query = "INSERT INTO mlocationhistory(locationdatetime, lat, lon, accuracy, userid, uniqueid, createddate, createdby) VALUES(@locationdatetime, @lat, @lon, @accuracy, @userid, @uniqueid, @createddate, @createdby)";
                QueryParameter[] sqlParams = new QueryParameter[]
                {
                    new QueryParameter("@locationdatetime", System.Data.SqlDbType.DateTime) {Value = Convert.ToDateTime(postLocation.androiddate.ToString())},
                    new QueryParameter("@lat", System.Data.SqlDbType.Float) {Value = postLocation.lat},
                    new QueryParameter("@lon", System.Data.SqlDbType.Float) {Value = postLocation.lon},
                    new QueryParameter("@accuracy", System.Data.SqlDbType.Float) {Value = postLocation.accuracy},
                    new QueryParameter("@createddate", System.Data.SqlDbType.DateTime) {Value = DateTime.Now},
                    new QueryParameter("@createdby", System.Data.SqlDbType.VarChar) {Value = postLocation.createdby},
                    new QueryParameter("@uniqueid", System.Data.SqlDbType.VarChar) {Value = postLocation.uniqueID},
                    new QueryParameter("@userid", System.Data.SqlDbType.VarChar) {Value = postLocation.userid}
                };

                NawaDAO.ExecuteNonQuery(query, sqlParams);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }
        }
    }
}
