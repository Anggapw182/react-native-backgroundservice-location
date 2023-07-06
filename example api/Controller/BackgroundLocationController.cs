using Microsoft.AspNetCore.Mvc;
using static NawaDataProject.Controllers.CustomController;
using NawaDataProjectDAL;
using NawaDataProjectBLL;

namespace NawaDataProject.Controllers.Location
{
    [Route("api/[controller]")]
    [ApiController]
    public class BackgroundLocationController : ControllerBase
    {

        [Route("LocationLog")]
        [HttpPost]
        public IActionResult LocationLog(PostLocation postLocation)
        {
            MLocationHistoryBLL mLocationHistoryBLL = new MLocationHistoryBLL();     
            mLocationHistoryBLL.MlocationLog(postLocation);
            return Ok();
        }
    }
}
