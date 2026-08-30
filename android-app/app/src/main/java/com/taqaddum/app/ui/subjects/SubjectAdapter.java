package com.taqaddum.app.ui.subjects;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.taqaddum.app.R;
import com.taqaddum.app.data.Subject;
import com.taqaddum.app.databinding.ItemSubjectBinding;
public class SubjectAdapter extends ListAdapter<Subject, SubjectAdapter.Holder> {
    public interface Listener { void onSubjectClicked(Subject subject); }
    private final Listener listener;
    public SubjectAdapter(Listener listener) { super(DIFF); this.listener = listener; }
    public Subject subjectAt(int position) { return getItem(position); }
    public void restore(Subject subject) { int position = getCurrentList().indexOf(subject); if (position >= 0) notifyItemChanged(position); }
    private static final DiffUtil.ItemCallback<Subject> DIFF = new DiffUtil.ItemCallback<Subject>() {
        public boolean areItemsTheSame(@NonNull Subject a,@NonNull Subject b){return a.id==b.id;}
        public boolean areContentsTheSame(@NonNull Subject a,@NonNull Subject b){return a.name.equals(b.name)&&a.weeklyTargetMinutes==b.weeklyTargetMinutes&&a.archived==b.archived&&a.colorHex.equals(b.colorHex)&&a.hasTheory==b.hasTheory&&a.hasPractical==b.hasPractical;}
    };
    @NonNull public Holder onCreateViewHolder(@NonNull ViewGroup p,int type){return new Holder(ItemSubjectBinding.inflate(LayoutInflater.from(p.getContext()),p,false));}
    public void onBindViewHolder(@NonNull Holder h,int pos){h.bind(getItem(pos));}
    class Holder extends RecyclerView.ViewHolder { final ItemSubjectBinding b; Holder(ItemSubjectBinding b){super(b.getRoot());this.b=b;} void bind(Subject s){b.subjectName.setText(s.name);b.subjectTarget.setText(b.getRoot().getContext().getString(R.string.weekly_target_format,s.weeklyTargetMinutes));String sections=s.hasTheory&&s.hasPractical?"نظري • عملي":s.hasTheory?"نظري":"عملي";b.subjectSections.setText(sections);try{b.subjectCard.setCardBackgroundColor(Color.parseColor(s.colorHex));}catch(Exception ignored){b.subjectCard.setCardBackgroundColor(Color.parseColor("#FFF1A8"));}b.getRoot().setOnClickListener(v->listener.onSubjectClicked(s));}}
}
