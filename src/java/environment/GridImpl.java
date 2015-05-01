package java.environment;

import speadl.environment.Grid;

public class GridImpl extends Grid{

	@Override
	protected IBoxGenerator make_createBox() {
		return null;
	}

	@Override
	protected INestCreator make_createNests() {
		// TODO Auto-generated method stub
		return new INestCreator() {

			@Override
			public void createAllNests() {
				// TODO Auto-generated method stub
				
			}
			
		};
	}

}
