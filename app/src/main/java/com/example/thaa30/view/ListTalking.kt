package com.example.thaa30.view


import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thaa30.R
import com.example.thaa30.util.Convers
import com.example.thaa30.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.talking_list.*

class ListTalking() : Fragment(), Parcelable {
    lateinit var viewModel:ListViewModel
    private val listAdapter=ConverListAdapter(arrayListOf())

    private val animalListDataObserver=Observer<List<Convers>>{list->
        list?.let {
            conversList.visibility=View.VISIBLE
            listAdapter.updateConverList(it)
        }

    }
    private val loadingDataObserver=Observer<Boolean>{isLoading->
        loadingView.visibility=if (isLoading) View.VISIBLE else View.GONE
        if (isLoading){
            listError.visibility= View.GONE
            conversList.visibility= View.GONE
        }

    }
    private val errorDataObserver= Observer<Boolean>{ isError->
        listError.visibility=if (isError) View.VISIBLE else View.GONE
        if (isError){
            loadingView.visibility= View.GONE
            conversList.visibility= View.GONE
        }

    }

    constructor(parcel: Parcel) : this() {

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.talking_list, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.convers.observe(this,animalListDataObserver)
        viewModel.loading.observe(this,loadingDataObserver)
        viewModel.loadError.observe(this,errorDataObserver)
        viewModel.refresh()
        conversList.apply {
            layoutManager= LinearLayoutManager (context)
            adapter=listAdapter
        }
        refreshLayout.setOnRefreshListener {
            conversList.visibility= View.GONE
            listError.visibility= View.GONE
            loadingView.visibility= View.VISIBLE
            viewModel.refresh()
            refreshLayout.isRefreshing=false
        }


        val conv= Convers(1,"tada","rama")
        val action=ListTalkingDirections.actionToDetails(conv)
        Navigation.findNavController(view).navigate(action)


    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ListTalking> {
        override fun createFromParcel(parcel: Parcel): ListTalking {
            return ListTalking(parcel)
        }

        override fun newArray(size: Int): Array<ListTalking?> {
            return arrayOfNulls(size)
        }
    }

}
