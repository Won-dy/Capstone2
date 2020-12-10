package kr.co.ilg.activity.workermanage;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
//import android.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone2.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import kr.co.ilg.activity.findwork.PickStateRVItem;

public class FieldWorkerListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
Context context;
    Context psContext;
    View dialogView;
    Button btnPay, btnReview, btnCall;
    Intent intent;
    View clickedView;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView profileIV;
        TextView wkName, wkAge, wkPNum;
        ImageButton arrowRBtn;
        LinearLayout btnWorkerPay, btnWorkerCall, btnWorkerReview, expanded_menu;
        ImageView choolImage, toiImage, payImage;
        TextView choolText, toiText;
        MyViewHolder(View view) {
            super(view);
            profileIV = view.findViewById(R.id.profileIV);
            wkName = view.findViewById(R.id.wkName);
            wkAge = view.findViewById(R.id.wkAge);
            wkPNum = view.findViewById(R.id.wkPNum);
            arrowRBtn = view.findViewById(R.id.arrowRBtn);
            expanded_menu = view.findViewById(R.id.expanded_menu);
            btnWorkerPay = view.findViewById(R.id.btnWorkerPay);
            btnWorkerCall = view.findViewById(R.id.btnWorkerCall);
            btnWorkerReview = view.findViewById(R.id.btnWorkerReview);
            choolImage = view.findViewById(R.id.choolImage);
            choolText = view.findViewById(R.id.choolText);
            toiImage = view.findViewById(R.id.toiImage);
            toiText = view.findViewById(R.id.toiText);
            payImage=view.findViewById(R.id.payImage);
        }
    }

    private ArrayList<PickStateRVItem> wkList;

    public FieldWorkerListAdapter(Context c, ArrayList<PickStateRVItem> wkList) {
        this.psContext = c;
        this.wkList = wkList;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.field_workerlist_custom, parent, false);

        return new FieldWorkerListAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FieldWorkerListAdapter.MyViewHolder myViewHolder = (FieldWorkerListAdapter.MyViewHolder) holder;


        //출퇴근, 구직자 이메일, 구직자 이름 가져오기, 지급여부
        myViewHolder.profileIV.setImageResource(wkList.get(position).pofileIMGId);
        myViewHolder.wkName.setText(wkList.get(position).wkName);
        myViewHolder.wkAge.setText(wkList.get(position).wkAge);
        myViewHolder.wkPNum.setText(wkList.get(position).wkPNum);
        String wk_email = wkList.get(position).wk_email;

        myViewHolder.arrowRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, GujicProfileForGuin.class);
                intent.putExtra("wk_email", wk_email);
                context.startActivity(intent);
            }
        });
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context =v.getContext();

                //전에 클릭한 것이 없을 때
                if(clickedView==null)
                {
                    clickedView=myViewHolder.expanded_menu;
                    changeVisibility(true,clickedView);
                }
                else//전에 클릭한 것이 있을 때
                {
//                        같은 거 클릭했을 때
                    if(clickedView == myViewHolder.expanded_menu) {
                        changeVisibility(false,clickedView);
                        clickedView = null;
                    }
                    //다른 거 클릭했을 때
                    else {
                        changeVisibility(true,myViewHolder.expanded_menu);;
                        changeVisibility(false,clickedView);;
                        clickedView = myViewHolder.expanded_menu;

                    }
                }



                if(wkList.get(position).mf_is_choolgeun.equals("1"))
                {
                    myViewHolder.choolText.setText("출근완료");
                    //myViewHolder.choolImage.setImageTintBlendMode();
                    myViewHolder.choolImage.setColorFilter(Color.parseColor("#2EC5D4"));
                }
                if(wkList.get(position).mf_is_toigeun.equals("1"))
                {
                    myViewHolder.toiText.setText("퇴근완료");
                    myViewHolder.toiImage.setColorFilter(Color.parseColor("#2EC5D4"));
                }
                if(wkList.get(position).mf_is_paid.equals("1"))
                {
                    myViewHolder.payImage.setColorFilter(Color.parseColor("#2EC5D4"));
                }

                myViewHolder.btnWorkerPay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context =v.getContext();
                        intent = new Intent(context, PayActivity.class);
                        intent.putExtra("wkName",wkList.get(position).wkName);
                        intent.putExtra("worker_bankname",wkList.get(position).worker_bankname);
                        intent.putExtra("worker_bankaccount",wkList.get(position).worker_bankaccount);
                        intent.putExtra("wk_email",wkList.get(position).wk_email);
                        intent.putExtra("field_code",wkList.get(position).field_code);
                        context.startActivity(intent);
                    }
                });

                myViewHolder.btnWorkerReview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        context =v.getContext();
                        intent = new Intent(context, UserReviewWriteActivity.class);
                        intent.putExtra("worker_name",wkList.get(position).wkName);
                        intent.putExtra("worker_email",wkList.get(position).wk_email);
                        intent.putExtra("jp_num",wkList.get(position).jp_num);
                        context.startActivity(intent);
                    }
                });

                myViewHolder.btnWorkerCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("tel:" + wkList.get(position).wkPNum);
                        intent = new Intent(Intent.ACTION_DIAL, uri);
                        context.startActivity(intent);
                    }
                });


            }
        });
    }
    private void changeVisibility(final boolean isExpanded, View view) {
        // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
        ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, 300) : ValueAnimator.ofInt(600, 0);
        // Animation이 실행되는 시간, n/1000초
        va.setDuration(200);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.getLayoutParams().height = (int) animation.getAnimatedValue();
                view.requestLayout();
                view.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            }
        });
        // Animation start
        va.start();
    }
    @Override
    public int getItemCount() {
        return wkList.size();
    }
}
