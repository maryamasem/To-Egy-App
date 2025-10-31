import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.depi.toegy.R
import kotlinx.coroutines.launch

@Composable
fun onBoardingScreen() {
    val pagerState: PagerState = rememberPagerState(initialPage = 0) {
        3
    }
    val coroutineScope= rememberCoroutineScope()
    val context= LocalContext.current
    Box(modifier = Modifier.fillMaxHeight()) {
        HorizontalPager(state = pagerState) { page ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                var resource: Int = R.drawable.pyramids
                var text: String = ""
                var description:String=""

                when (page) {
                    0 -> {
                        text = "TO EGY"
                        resource = R.drawable.pyramids
                        description="Your Smart Guide in Egypt"

                    }
                    1 -> {
                        text = "Discover Attraction"
                        resource = R.drawable.museum
                        description="Explore Egypt like never before!         To EGY  is your personal travel guide  helping you discover the most famous attractions, hidden gems, and cultural landmarks across Egypt From ancient temples and museums to breathtaking beaches and vibrant local spots. "
                    }
                    2 -> {
                        text = "Plan Your Trips"
                        resource = R.drawable.nile
                        description="Your next adventure starts here!" +
                                "Choose your destinations, organize your plans, and make every trip unforgettable "
                    }
                }



                Column(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(40.dp))
                    Text(text = text, fontSize = 35.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.SansSerif, modifier = Modifier.padding(bottom = 15.dp, top = 35.dp) )
                    Image(painter = painterResource(id=resource), contentDescription = "OnBoarding Screens",
                        contentScale =  ContentScale.Crop,modifier=Modifier.fillMaxWidth().height(410.dp).clip(RoundedCornerShape(16.dp))

                    )
                    Spacer(modifier=Modifier.height(10.dp))
                    Box(modifier = Modifier.fillMaxWidth().padding(bottom=2.dp).height(150.dp).verticalScroll(
                        rememberScrollState()
                    ), contentAlignment = Alignment.TopCenter){Text(text = description, fontSize = 15.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Serif, lineHeight = 20.sp )}
                    Spacer(modifier = Modifier.height(30.dp))





                }
            }
        }

            Row(modifier=Modifier.fillMaxWidth().align(Alignment.BottomCenter).padding(horizontal = 20.dp, vertical = 120.dp), horizontalArrangement = Arrangement.End) {
                AnimatedVisibility(
                    visible = (pagerState.currentPage == 2),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Button(onClick = {// val intent=Intent(context,SignUpActivity::class.java)
                        //context.startActivity(intent)
                    },


                        modifier = Modifier.width(80.dp).height(40.dp)) {
                        Text(
                            "Next",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,


                        )
                    }
                }
            }
            Row(modifier=Modifier.align(Alignment.BottomCenter) .padding(bottom=80.dp), horizontalArrangement = Arrangement.Center){
                repeat(3) {
                    CustomIndicator(isSelected = pagerState.currentPage == it)
                    Spacer(modifier=Modifier.size(2.5.dp))

                }
            }
        }
    }


@Composable
fun CustomIndicator(isSelected: Boolean) {

    Box(modifier = Modifier.height(10.dp).width(width= if(isSelected)20.dp else 15.dp).clip(RoundedCornerShape(10.dp)).background(color=if(isSelected) Color(
        0xFF0C0B0C
    ) else Color.LightGray))
    Spacer(modifier=Modifier.size(4.dp).padding(bottom = 100.dp,top=100.dp))
}
@Preview(showBackground = true)
@Composable
fun onBoardingScreenPreview(){
    onBoardingScreen()
}


