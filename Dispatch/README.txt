2/6/2015
    Right now, I'm just laying down some of the GUI that will 
    probably change 90% after we get the full spec. As of now,
    I'm just kind of sure of what the GUI for dispatch will 
    look like, from the ppt presentation slide. 
    
    Working idea:
    
    Dispatch will keep track of field supervisor dispatch
    operations. Right now, I think that's just dropping
    off and picking up cash. So, we need to keep track of
    dispatch requests(when, from who, how much, field supe
    sent). May want to have a field somewhere that has access
    to how much money is out there, a running total of the 
    negative balance.
    
    Robustness:
    A)Missing money:
    	I)Must keep a record of how much each club has taken.
    	II)Must make sure that requests and transactions
    	 	are confirmed from when the order is placed and when
    	 	money is moved.
    	 	   -Will think about this more.
    	III)Should we keep track of when an FS is dispatched, when
    		the transfer is made with the club, and when the money 
    		brought back? More moving parts, but more transparency.
    
    B)Problems with program: 
    	I) If field supe is already dispatched, don't allow to 
    		be dispatched again until order is completed.
    		 -If things get hectic, a field supe might try to
    		 take care of multiple orders at the same time. This 
    		 includes some risk, but may be more practical. Might
    		 have to create a robust solution to allow for this.
    	II) If a dispatch is incorrectly performed, must allow for
    		and undo.
    		  -I think it's best to only allow an undo for the 
    		  last dispatch command.
    		  -On the other hand, it could simply be a matter of
    		  showing the dispatch complete, and then rebalancing
    		  the budget as necessary.
    		  -In any case, undos should be logged. 
    
    Objects:
    FieldSupe(String name, 
    			int current_wallet)
    			
    			name - Field Supe's name
    			current_wallet - Keeps track of money currently
    				in hand. Might not need this. 
    				
    			We might be able to just work with a string for
    			FieldSupes if we just need their names.
    
    Dispatch_command(int dispatch_number,
    					String fieldSupeID,
    					String action,
    					int, time_in)
    					
    				dispatch_number - for identification
    				fieldSupeID - string for field supe
    				action - I believe there's just giving money 
    					and collecting money
    				time_in - this was on the ppt. 