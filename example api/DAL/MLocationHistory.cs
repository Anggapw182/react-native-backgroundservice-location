using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace NawaDataProjectDAL
{
    public class MLocationHistory
    {
        public Int32 pk_location_id { get; set; }
        public Nullable<TimeSpan> locationdatetime { get; set; }
        public Nullable<float> lat { get; set; }
        public Nullable<float> lon {get;set;}
        public Nullable<float> accuracy { get; set; }
    }

    public class PostLocation
    {
        public float lat { get; set; }
        public float lon { get; set; }
        public float accuracy { get; set; }
        public string androiddate { get; set; }
        public string uniqueID { get; set; }
        public string userid { get; set; }
        public string createdby { get; set; }
    }
}
