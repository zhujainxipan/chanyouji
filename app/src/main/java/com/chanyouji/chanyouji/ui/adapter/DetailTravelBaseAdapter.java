package com.chanyouji.chanyouji.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import com.chanyouji.chanyouji.R;
import com.chanyouji.chanyouji.model.travel.Text;
import com.chanyouji.chanyouji.model.travel.ZiDingYiTravel;
import com.chanyouji.chanyouji.thread.CacheImageAsyncTask;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

//StickyListHeadersAdapter.这是开源库提供的接口，因为我们需要添加Header，
//所以我们必须在Adapter中也返回一个Header的View，这其实跟实现getView是一样的道理的，都挺好理解的。

//SectionIndexer需要实现三个方法：
// getSections()：返回的其实就是Header上面要展示的数据
// getPositionForSection(int sectionIndex)：返回的是这个section数据在List<TodoTask>
// 这个基础数据源中的位置，因为section中的数据其实也是从List<TodoTask>中获取到的。
//getSectionForPosition(int position)：则是通过在基础数据源List<TodoTask>中的位置找出对应的Section中的数据
//BaseAdapter就是我们以前自定义view时的做法，这里只不过需要多实现两个接口（来自于第三方）

public class DetailTravelBaseAdapter extends BaseAdapter implements
		StickyListHeadersAdapter, SectionIndexer {

	//我们传进来的就是list,因为这才是真正的数据，我们分组也是基于这个数据的
	List<ZiDingYiTravel> list;

	private final Context context;

	//group对应的在数据源中的第几行（不计算group的行）
	//即用来存放每一轮分组的第一个item的位置
	private int[] sectionIndices;

	//group对应的数据源
	//用来存放每一个分组要展现的数据，因为能够分到同一组的item
	//他们肯定有一个相同且可以跟其他section区别开来的值
	//比如这里，我利用day来分成不同的分组的，所以sectionHeaders存放的
	//只是day和starttime
	//不过这里大家注意：基于某个字段的分组，这个数据源必须是在这个字段上是
	//有序的！
	private int[] sectionHeaders;


	private LayoutInflater inflater;

	public DetailTravelBaseAdapter(Context context, List<ZiDingYiTravel> list) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);

		sectionIndices = getSectionIndices();
		sectionHeaders = getSectionHeaders();

	}

	/**
	 * 已经修改正确
	 * @return
	 */
	private int[] getSectionIndices() {
		ArrayList<Integer> sectionIndices = new ArrayList<Integer>();
		int day = list.get(0).getDay();
		sectionIndices.add(0);

		for (int i = 1; i < list.size(); i++) {
			int groupDay = list.get(i).getDay();
			if (day != groupDay) {
				day = groupDay;
				sectionIndices.add(i);
			}
		}

		int[] sections = new int[sectionIndices.size()];
		for (int i = 0; i < sectionIndices.size(); i++) {
			sections[i] = sectionIndices.get(i);
		}
		return sections;
	}


	/**
	 * 修改完毕
	 * @return
	 */
	private int[] getSectionHeaders() {
		int[] sectionHeaders = new int[sectionIndices.length];
		for (int i = 0; i < sectionIndices.length; i++) {
			sectionHeaders[i] = list.get(sectionIndices[i]).getDay();
		}
		return sectionHeaders;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return list.get(position).getId();
	}


	@Override
	public int getViewTypeCount() {
		return 5;
	}

	@Override
	public int getItemViewType(int position) {
		//根据当前的数据，判断类型，并且返回
		int ret;
		ZiDingYiTravel ziDingYiTravel = list.get(position);
			if (ziDingYiTravel.getImgUrl() == null) {
				//这里的说明没有图片
				if (ziDingYiTravel.getEntryname() == null) {
					//说明只有文字
					ret = 1;
				} else {
					//说明是文字配地点
					ret = 2;
				}

			} else {
				//这里的说明有图片
				if (ziDingYiTravel.getEntryname() == null || ziDingYiTravel.getEntryname() == "") {
					//说明没有地点，只有图片
					ret = 4;
				} else if (ziDingYiTravel.getDescription() == "" || ziDingYiTravel.getDescription() == null) {
					//说明只有图片
					ret = 3;
				} else {
					//说明图片+地点
					ret = 0;
				}
		}
		return ret;
	}

	/**
	 * 多布局的复用问题
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View ret = null;
		int index = getItemViewType(position);
		ZiDingYiTravel ziDingYiTravel = list.get(position);
		switch (index) {
			case 1:
				//说明只有文本
				//1、视图复用,convertView转化
				if (convertView != null) {
					ret = convertView;
				} else {
					ret = LayoutInflater
							.from(context)
							.inflate(R.layout.detail_travel_list_onlytext_item, parent, false);
				}

				////////////////////////

				// 2. 处理 ViewHolder 减少 findViewById
				OnlyTextViewHolder holder = (OnlyTextViewHolder) ret.getTag();
				//初始化viewholder
				if (holder == null) {
					holder = new OnlyTextViewHolder();
					holder.contentTxt = (TextView) ret.findViewById(R.id.detail_travel_list_onlytext_item_txt);
					ret.setTag(holder);
				}

				/////////////////////////////
				// 3. 显示内容
				String description = ziDingYiTravel.getDescription();
				if (description != null && description != "") {
					holder.contentTxt.setText(description);
				}
				break;
			case 2:
				//文本+地点
				//1、视图复用,convertView转化
				if (convertView != null) {
					ret = convertView;
				} else {
					ret = LayoutInflater
							.from(context)
							.inflate(R.layout.detail_travel_list_portwithtextwithloc_item, parent, false);
				}

				////////////////////////

				// 2. 处理 ViewHolder 减少 findViewById
				PortWithTextWithLocViewHolder holder1 = (PortWithTextWithLocViewHolder) ret.getTag();
				//初始化viewholder
				if (holder1 == null) {
					holder1 = new PortWithTextWithLocViewHolder();
					holder1.contentTxt = (TextView) ret.findViewById(R.id.detail_travel_list_portwithtextwithloc_item_txt);
					holder1.locTxt = (TextView) ret.findViewById(R.id.detail_travel_list_portwithtextwithloc_item_loc);
					holder1.portTxt = (TextView) ret.findViewById(R.id.detail_travel_list_portwithtextwithloc_item_port);
					ret.setTag(holder1);
				}

				/////////////////////////////
				// 3. 显示内容
				String description1 = ziDingYiTravel.getDescription();
				if (description1 != null && description1 != "") {
					holder1.contentTxt.setText(description1);
				}

				String entryname = ziDingYiTravel.getEntryname();
				if (entryname != null) {
					holder1.portTxt.setText(entryname);
					holder1.locTxt.setText(entryname);
				}

				break;
			case 3:
				//图片+地点
				//1、视图复用,convertView转化
				if (convertView != null) {
					ret = convertView;
				} else {
					ret = LayoutInflater
							.from(context)
							.inflate(R.layout.detail_travel_list_imgwithloc_item, parent, false);
				}

				////////////////////////

				// 2. 处理 ViewHolder 减少 findViewById
				ImgWithLocViewHolder holder2 = (ImgWithLocViewHolder) ret.getTag();
				//初始化viewholder
				if (holder2 == null) {
					holder2 = new ImgWithLocViewHolder();
					holder2.locTxt = (TextView) ret.findViewById(R.id.detail_travel_list_imgwithloc_item_loc);
					holder2.portTxt = (TextView) ret.findViewById(R.id.detail_travel_list_imgwithloc_item_port);
					holder2.imgImg = (ImageView) ret.findViewById(R.id.detail_travel_list_imgwithloc_item_img);
					ret.setTag(holder2);
				}

				/////////////////////////////
				// 3. 显示内容

				holder2.imgImg.setImageResource(R.mipmap.test);

				String entryname1 = ziDingYiTravel.getEntryname();
				if (entryname1 != null) {
					holder2.portTxt.setText(entryname1);
					holder2.locTxt.setText(entryname1);
				}

				//显示图片
				String url = ziDingYiTravel.getImgUrl();
				//为了避免图片错乱的问题，需要给每一次加载图片时，imageview设置加载网址的tag
				//通过tag传递给task，进行检查
				holder2.imgImg.setTag(url);
				if (url != null) {
					//todo 加载图片，并设置到imageview
					CacheImageAsyncTask task = new CacheImageAsyncTask(holder2.imgImg, context);
					task.execute(url);
				}


				break;
			case 4:
				//图片+文本+地点
				//1、视图复用,convertView转化
				if (convertView != null) {
					ret = convertView;
				} else {
					ret = LayoutInflater
							.from(context)
							.inflate(R.layout.detail_travel_list_onlyimg_item, parent, false);
				}

				////////////////////////

				// 2. 处理 ViewHolder 减少 findViewById
				OnlyImgViewHolder holder4 = (OnlyImgViewHolder) ret.getTag();
				//初始化viewholder
				if (holder4 == null) {
					holder4 = new OnlyImgViewHolder();
					holder4.imgImg = (ImageView) ret.findViewById(R.id.detail_travel_list_onlyimg_item_img);
					ret.setTag(holder4);
				}

				/////////////////////////////
				// 3. 显示内容

				holder4.imgImg.setImageResource(R.mipmap.test);


				//显示图片
				String url3 = ziDingYiTravel.getImgUrl();
				//为了避免图片错乱的问题，需要给每一次加载图片时，imageview设置加载网址的tag
				//通过tag传递给task，进行检查
				holder4.imgImg.setTag(url3);
				if (url3 != null) {
					//todo 加载图片，并设置到imageview
					CacheImageAsyncTask task = new CacheImageAsyncTask(holder4.imgImg, context);
					task.execute(url3);
				}


				break;
			case 0:
				//图片+文本+地点
				//1、视图复用,convertView转化
				if (convertView != null) {
					ret = convertView;
				} else {
					ret = LayoutInflater
							.from(context)
							.inflate(R.layout.detail_travel_list_imgwithtext_item, parent, false);
				}

				////////////////////////

				// 2. 处理 ViewHolder 减少 findViewById
				ImgWithTextViewHolder holder3 = (ImgWithTextViewHolder) ret.getTag();
				//初始化viewholder
				if (holder3 == null) {
					holder3 = new ImgWithTextViewHolder();
					holder3.locTxt = (TextView) ret.findViewById(R.id.detail_travel_list_imgwithtext_item_loc);
					holder3.portTxt = (TextView) ret.findViewById(R.id.detail_travel_list_imgwithtext_item_port);
					holder3.imgImg = (ImageView) ret.findViewById(R.id.detail_travel_list_imgwithtext_item_img);
					holder3.contentTxt = (TextView) ret.findViewById(R.id.detail_travel_list_imgwithtext_item_txt);
					ret.setTag(holder3);
				}

				/////////////////////////////
				// 3. 显示内容

				holder3.imgImg.setImageResource(R.mipmap.test);

				String entryname2 = ziDingYiTravel.getEntryname();
				if (entryname2 != null) {
					holder3.portTxt.setText(entryname2);
					holder3.locTxt.setText(entryname2);
				}

				String description2 = ziDingYiTravel.getDescription();
				if (description2 != null && description2 != "") {
					holder3.contentTxt.setText(description2);
				}

				//显示图片
				String url2 = ziDingYiTravel.getImgUrl();
				//为了避免图片错乱的问题，需要给每一次加载图片时，imageview设置加载网址的tag
				//通过tag传递给task，进行检查
				holder3.imgImg.setTag(url2);
				if (url2 != null) {
					//todo 加载图片，并设置到imageview
					CacheImageAsyncTask task = new CacheImageAsyncTask(holder3.imgImg, context);
					task.execute(url2);
				}

				break;
		}


		return ret;
	}

	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		HeaderViewHolder holder;

		if (convertView == null) {
			holder = new HeaderViewHolder();
			convertView = inflater.inflate(R.layout.detail_travel_group_header, parent, false);
			holder.text1 = (TextView) convertView.findViewById(R.id.text1);
			holder.text2 = (TextView) convertView.findViewById(R.id.text2);
			convertView.setTag(holder);
		} else {
			holder = (HeaderViewHolder) convertView.getTag();
		}
		holder.text1.setText("DAY" + list.get(position).getDay() + "  ");
		holder.text2.setText(list.get(position).getTripdate());

		return convertView;
	}

	@Override
	public long getHeaderId(int position) {
		return list.get(position).getDay();
	}

	@Override
	public Object[] getSections() {
		return new int[][]{sectionHeaders};
	}

	@Override
	public int getPositionForSection(int section) {
		if (section >= sectionIndices.length) {
			section = sectionIndices.length - 1;
		} else if (section < 0) {
			section = 0;
		}
		return sectionIndices[section];
	}

	@Override
	public int getSectionForPosition(int position) {
		for (int i = 0; i < sectionIndices.length; i++) {
			if (position < sectionIndices[i]) {
				return i - 1;
			}
		}
		return sectionIndices.length - 1;
	}


	class HeaderViewHolder {
		TextView text1;
		TextView text2;
	}



	class ImgWithLocViewHolder {
		TextView portTxt;
		ImageView imgImg;
		TextView locTxt;
		TextView lickCountTxt;
		TextView commCountTxt;
	}

	class OnlyImgViewHolder {
		ImageView imgImg;
		TextView lickCountTxt;
		TextView commCountTxt;
	}

	class ImgWithTextViewHolder {
		TextView portTxt;
		ImageView imgImg;
		TextView contentTxt;
		TextView locTxt;
		TextView lickCountTxt;
		TextView commCountTxt;
	}


	class OnlyTextViewHolder {
		TextView contentTxt;
		TextView lickCountTxt;
		TextView commCountTxt;
	}

	class PortWithTextWithLocViewHolder {
		TextView portTxt;
		TextView contentTxt;
		TextView lickCountTxt;
		TextView commCountTxt;
		TextView locTxt;
	}

}
