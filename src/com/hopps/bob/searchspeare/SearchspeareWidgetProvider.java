package com.hopps.bob.searchspeare;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.hopps.bob.searchspeare.QuoteDatabase.DictionaryOpenHelper;

public class SearchspeareWidgetProvider extends AppWidgetProvider {
	
		private static DictionaryOpenHelper doh;
	
		public void onDeleted(Context context, int[] appWidgetIds) {
		    super.onDeleted(context, appWidgetIds);
		    Toast.makeText(context, "widget deleted", Toast.LENGTH_SHORT).show();
		}	
		
		public static void setDictionaryOpenHelper(DictionaryOpenHelper d)
		{
			doh = d;
		}
	
	    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
	        final int N = appWidgetIds.length;

	        // Perform this loop procedure for each App Widget that belongs to this provider
	        for (int i=0; i<N; i++) {
	            int appWidgetId = appWidgetIds[i];
	            
	            Cursor cursor = doh.getReadableDatabase().query(QuoteDatabase.FTS_VIRTUAL_TABLE, null, null, null, null, null, "RANDOM()", "1");

	            // Get the layout for the App Widget and attach an on-click listener
	            // to the button
	            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.searchspeare_appwidget);
	            int qIndex = cursor.getColumnIndexOrThrow(QuoteDatabase.KEY_QUOTE);
	            int lIndex = cursor.getColumnIndexOrThrow(QuoteDatabase.KEY_LOCATION);
	            views.setTextViewText(R.id.widquote, cursor.getString(qIndex));
	            views.setTextViewText(R.id.widloc, cursor.getString(lIndex));
	            doh.close();

	            // Tell the AppWidgetManager to perform an update on the current app widget
	            appWidgetManager.updateAppWidget(appWidgetId, views);
	        }
	    }
	}


